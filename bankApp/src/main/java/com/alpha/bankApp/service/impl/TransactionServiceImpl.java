package com.alpha.bankApp.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.TransactionDao;
import com.alpha.bankApp.dto.AccountStatementDto;
import com.alpha.bankApp.dto.StatementDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.Beneficiary;
import com.alpha.bankApp.entity.Deposit;
import com.alpha.bankApp.entity.FundTransfer;
import com.alpha.bankApp.entity.Statement;
import com.alpha.bankApp.entity.Transaction;
import com.alpha.bankApp.enums.AccountType;
import com.alpha.bankApp.enums.FundMessage;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.enums.TransactionStatus;
import com.alpha.bankApp.enums.TransactionType;
import com.alpha.bankApp.exception.InsufficientBalanceException;
import com.alpha.bankApp.exception.UserAccountNotFoundException;
import com.alpha.bankApp.exception.UserBeneficiaryNotFound;
import com.alpha.bankApp.exception.UserLacksTransactions;
import com.alpha.bankApp.service.TransactionService;
import com.alpha.bankApp.util.AccountUtil;
import com.alpha.bankApp.util.ResponseStructure;
import com.alpha.bankApp.util.TransactionUtil;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountUtil accountUtil;
	@Autowired
	private BankAccountDao bankAccountDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private TransactionUtil transactionUtil;
	@Autowired
	private TransactionDao transactionDao;

	@Override
	public ResponseEntity<ResponseStructure<String>> deposit(String accountNumber, Deposit deposit) {
		Account account = accountDao.getAccountByAccountNumber(accountNumber);
		if (account != null && account.getBranch() != null && account.getStatus().equals(Status.ACTIVE)) {
			Statement statement = account.getStatement();
			if (statement == null) {
				statement = accountUtil.createStatement();
			}
			List<Transaction> transactions = statement.getTransactions();
			if (transactions == null) {
				transactions = new ArrayList<>();
			}
			// Deposit to the user account
			if (account.getAvailableBalance() == null) {
				account.setAvailableBalance(0.0);
			}
			/*
			 * made adjustments to the deposit functionality to ensure that the account
			 * balance is updated correctly based on the account type.
			 */
			if (account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)) {
				account.setAvailableBalance(account.getAvailableBalance() + deposit.getAmount());
			} else {
				account.setCurrentBalance(account.getCurrentBalance() + deposit.getAmount());
			}
			deposit.setStatus(TransactionStatus.SUCESS);
			deposit.setType(TransactionType.DEPOSIT);
			deposit.setEnedDate(LocalDateTime.now());
			deposit.setStatement(statement);
			deposit.setBalance(account.getAvailableBalance());
			transactions.add(deposit);
			statement.setTransactions(transactions);
			account.setStatement(statement);
			accountDao.updateAccount(accountNumber, account);

			// Deposit to the bankAccount
			Optional<BankAccount> optional = bankAccountDao
					.getBankAccountByBankId((bankDao.findBankIdByBranchId(account.getBranch().getBranchId())));
			BankAccount bankAccount = optional.get();
			bankAccount.setBankBalance(bankAccount.getBankBalance() + deposit.getAmount());
			bankAccount.setCashInFlow(bankAccount.getCashInFlow() + deposit.getAmount());
			bankAccountDao.updateBankAccountById(bankAccount.getBankAccountId(), bankAccount);
			ResponseStructure<String> structure = new ResponseStructure<>();
			structure.setData("Deposit Success");
			structure.setMessage("Success");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);

		}
		throw new UserAccountNotFoundException("Individual without an account or inactive user account.");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> fundTransfer(FundTransfer fundTransfer) {
		Account senderAccount = accountDao.getAccountByAccountNumber(fundTransfer.getSenderAccountNumber());
		if (senderAccount != null) {
			// Figuring out how much to transfer to the receiver.
			if (senderAccount.getAvailableBalance() > fundTransfer.getAmount()) {
				/*
				 * verifying that the sender has successfully created a beneficiary for the
				 * receiver account.
				 */
				List<Beneficiary> beneficiarys = senderAccount.getBeneficiary();
				if (beneficiarys != null && !(beneficiarys.isEmpty())
						&& transactionUtil.verifyBeneficiary(beneficiarys, fundTransfer.getReceiverAccountNumber())) {
					Account reviserAccount = accountDao
							.getAccountByAccountNumber(fundTransfer.getReceiverAccountNumber());
					if (reviserAccount != null) {
						// The sender's account balance will be debated.
						/*
						 * made adjustments to the FundTransfer functionality to ensure that the
						 * senderAccount balance is updated correctly based on the account type.
						 */
						if (senderAccount.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)) {
							senderAccount.setAvailableBalance(
									senderAccount.getAvailableBalance() - fundTransfer.getAmount());
						} else if (senderAccount.getAccountType().equals(AccountType.CURRENT_ACCOUNT)) {
							senderAccount
									.setCurrentBalance(senderAccount.getCurrentBalance() - fundTransfer.getAmount());
						}
						fundTransfer.setFundMessage(FundMessage.DEBITED);
						senderAccount = transactionUtil.generateStatement(senderAccount, fundTransfer);
						accountDao.updateAccount(senderAccount.getAccountNumber(), senderAccount);

						// The Receiver account balance will be credited.

						/*
						 * made adjustments to the FundTransfer functionality to ensure that the
						 * reviserAccount balance is updated correctly based on the account type.
						 */
						if (reviserAccount.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)) {
							reviserAccount.setAvailableBalance(
									reviserAccount.getAvailableBalance() + fundTransfer.getAmount());
						} else if (reviserAccount.getAccountType().equals(AccountType.CURRENT_ACCOUNT)) {
							reviserAccount
									.setCurrentBalance(reviserAccount.getCurrentBalance() + fundTransfer.getAmount());
						}
						FundTransfer reciverTransaction = transactionUtil.generateFundTransfer(fundTransfer);
						reciverTransaction.setFundMessage(FundMessage.CREDITED);
						reviserAccount = transactionUtil.generateStatement(reviserAccount, reciverTransaction);
						accountDao.updateAccount(reviserAccount.getAccountNumber(), reviserAccount);

						/*
						 * implementing a robust mechanism for updating BankAccount information
						 * following fund transactions.
						 */
						String senderBranchId = senderAccount.getBranch().getBranchId();
						String reviserBranchId = reviserAccount.getBranch().getBranchId();
						if (!senderBranchId.equals(reviserBranchId)) {
							String senderBankId = bankDao.findBankIdByBranchId(senderBranchId);
							String reviserBankId = bankDao.findBankIdByBranchId(reviserBranchId);
							if (!senderBankId.equals(reviserBankId)) {
								BankAccount senderBankAccount = bankAccountDao.getBankAccountByBankId(senderBankId)
										.get();
								BankAccount receiverBankAccount = bankAccountDao.getBankAccountByBankId(reviserBankId)
										.get();
								/*
								 * adjusting the bank account balance by Debating the amount from sender
								 * account.
								 */
								senderBankAccount
										.setBankBalance(senderBankAccount.getBankBalance() - fundTransfer.getAmount());
								senderBankAccount
										.setCashOutFlow(senderBankAccount.getCashOutFlow() + fundTransfer.getAmount());

								/*
								 * adjusting the bank account balance by crediting the amount to reviser
								 * account.
								 */
								receiverBankAccount.setBankBalance(
										receiverBankAccount.getBankBalance() + fundTransfer.getAmount());
								receiverBankAccount
										.setCashInFlow(receiverBankAccount.getCashInFlow() + fundTransfer.getAmount());
								/*
								 * Upon making changes to the bank account, ensure that the database is updated
								 * accordingly.
								 */
								bankAccountDao.updateBankAccountById(senderBankAccount.getBankAccountId(),
										senderBankAccount);
								bankAccountDao.updateBankAccountById(receiverBankAccount.getBankAccountId(),
										receiverBankAccount);
							}
						}
						ResponseStructure<String> structure = new ResponseStructure<>();
						structure.setData(fundTransfer.getAmount() + " Debited On " + LocalDateTime.now());
						structure.setMessage("DEBITED");
						structure.setStatusCode(HttpStatus.OK.value());
						return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
					}
					throw new UserAccountNotFoundException(
							"It appears that the account you are attempting to transfer funds to is either inactive or does not have the authority to receive the funds.");
				}
				throw new UserBeneficiaryNotFound(
						"Looks like the user forgot to set up a beneficiary for the receiver account.");
			}
			throw new InsufficientBalanceException("Looks like the account is running on empty.");
		}
		throw new UserAccountNotFoundException(
				"It seems that the account you're trying to use is either inactive or doesn't have the authority to transfer funds.");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<StatementDto>>> findAccountStatement(String accountNumber) {
		Statement statement = accountDao.findStatementByAccountId(accountNumber);
		List<Transaction> transactions = statement.getTransactions();
		if (transactions != null && !(transactions.isEmpty())) {
			ResponseStructure<List<StatementDto>> structure = new ResponseStructure<>();
			structure.setData(transactionUtil.createStatementDto(transactions));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<StatementDto>>>(structure, HttpStatus.OK);
		}
		throw new UserLacksTransactions(
				"No transactions. It's like trying to enter a secret club without the secret handshake!");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<StatementDto>>> findAccountStatement(
			AccountStatementDto accountStatementDto) {
		List<Transaction> transactions = transactionDao.findTransactions(accountStatementDto.getAccountNumber(),
				accountStatementDto.getStartDate().atTime(0, 0), accountStatementDto.getEndDate().atTime(0, 0));
		if (transactions != null && !(transactions.isEmpty())) {
			ResponseStructure<List<StatementDto>> structure = new ResponseStructure<>();
			structure.setData(transactionUtil.createStatementDto(transactions));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<StatementDto>>>(structure, HttpStatus.OK);
		}
		throw new UserLacksTransactions(
				"No transactions. It's like trying to enter a secret club without the secret handshake!");
	}

	/*
	 * This function will create an MS Excel file using the provided account number,
	 * start date, and end date.
	 */
	@Override
	public ResponseEntity<byte[]> findAccountStatementByExcelFormat(AccountStatementDto accountStatementDto) {

		List<Transaction> transactions = transactionDao.findTransactions(accountStatementDto.getAccountNumber(),
				accountStatementDto.getStartDate().atTime(0, 0), accountStatementDto.getEndDate().atTime(0, 0));
		List<StatementDto> statements = transactionUtil.createStatementDto(transactions);
		if (transactions != null && !(transactions.isEmpty())) {
			ByteArrayOutputStream outputStream = transactionUtil.generateExcel(statements);
			if (outputStream != null) {
				/*
				 * configured the content type and headers to MediaType.APPLICATION_OCTET_STREAM
				 * so that the Excel file can be provided as a response to the caller.
				 */
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				httpHeaders.setContentDispositionFormData("filename", "accountStatement.xlsx");
				return new ResponseEntity<>(outputStream.toByteArray(), httpHeaders, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		throw new UserLacksTransactions(
				"No transactions. It's like trying to enter a secret club without the secret handshake!");
	}

	/*
	 * This function will create an PDF file using the provided account number,
	 * start date, and end date.
	 */
	@Override
	public ResponseEntity<byte[]> findAccountStatementByPDFFormat(AccountStatementDto accountStatementDto) {

		List<Transaction> transactions = transactionDao.findTransactions(accountStatementDto.getAccountNumber(),
				accountStatementDto.getStartDate().atTime(0, 0), accountStatementDto.getEndDate().atTime(0, 0));
		List<StatementDto> statements = transactionUtil.createStatementDto(transactions);
		if (transactions != null && !(transactions.isEmpty())) {
			ByteArrayOutputStream outputStream = transactionUtil.generatePDF(statements);
			if (outputStream != null) {
				/*
				 * configured the content type and headers to MediaType.APPLICATION_PDF so that
				 * the PDF file can be provided as a response to the caller.
				 */
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDispositionFormData("filename", "accountStatement.pdf");

				return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		throw new UserLacksTransactions(
				"No transactions. It's like trying to enter a secret club without the secret handshake!");
	}

}
