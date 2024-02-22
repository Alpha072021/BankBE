package com.alpha.bankApp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dto.StatementDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Beneficiary;
import com.alpha.bankApp.entity.Deposit;
import com.alpha.bankApp.entity.FundTransfer;
import com.alpha.bankApp.entity.Statement;
import com.alpha.bankApp.entity.Transaction;
import com.alpha.bankApp.enums.FundMessage;
import com.alpha.bankApp.enums.TransactionStatus;
import com.alpha.bankApp.enums.TransactionType;

@Component
public class TransactionUtil {

	public Account generateStatement(Account account, FundTransfer fundTransfer) {
		Statement statement = account.getStatement();
		if (statement == null) {
			statement = new Statement();
			statement.setCreatedDateAndTime(LocalDateTime.now());
			statement.setTransactions(new ArrayList<>());
		}
		List<Transaction> transactions = statement.getTransactions();
		if (transactions == null) {
			transactions = new ArrayList<>();
		}

		fundTransfer.setCreatedDate(LocalDateTime.now());
		fundTransfer.setEnedDate(LocalDateTime.now());
		fundTransfer.setStatement(statement);
		fundTransfer.setStatus(TransactionStatus.SUCESS);
		fundTransfer.setType(TransactionType.FUNDTRANSFER);
		// Updating the account balance to the transaction
		if (account.getAvailableBalance() == null) {
			account.setAvailableBalance(0.0);
		}
		fundTransfer.setBalance(account.getAvailableBalance());
		transactions.add(fundTransfer);
		statement.setTransactions(transactions);
		account.setStatement(statement);
		return account;
	}

	public FundTransfer generateFundTransfer(FundTransfer fundTransfer) {

		return new FundTransfer(fundTransfer.getSenderAccountNumber(), fundTransfer.getReceiverAccountNumber(),
				fundTransfer.getAmount(), null);
	}

	public boolean verifyBeneficiary(List<Beneficiary> beneficiarys, String receiverAccountNumber) {
		List<Beneficiary> isBeneficiaryExist = beneficiarys.stream()
				.filter(beneficiary -> beneficiary.getReciverAccountNumber().equals(receiverAccountNumber))
				.collect(Collectors.toList());
		return !(isBeneficiaryExist.isEmpty());
	}

	/*
	 * This function is intended to create a list of StatementDto objects by
	 * processing a list of Transaction Info.
	 */
	public List<StatementDto> createStatementDto(List<Transaction> transactions) {
		List<StatementDto> statementDtos = new ArrayList<>();
		for (Transaction transaction : transactions) {
			statementDtos.add(createStatementDto(transaction));
		}
		return statementDtos;
	}

	/*
	 * This function is intended to create a StatementDto objects by processing a
	 * Transaction Info.
	 */
	public StatementDto createStatementDto(Transaction transaction) {
		StatementDto statementDto = new StatementDto();
		statementDto.setDate(transaction.getCreatedDate());
		statementDto.setBalance(transaction.getBalance());
		if (Deposit.class.isInstance(transaction)) {
			Deposit deposit = (Deposit) transaction;
			statementDto.setNarration("Deposit/" + deposit.getTransactionId());
			statementDto.setDebit(deposit.getAmount());
		} else if (FundTransfer.class.isInstance(transaction)) {
			FundTransfer fundTransfer = (FundTransfer) transaction;
			// Need to modify the things
			if (fundTransfer.getFundMessage() != null) {
				if (fundTransfer.getFundMessage().equals(FundMessage.DEBITED)) {
					statementDto.setNarration(fundTransfer.getFundMessage().toString() + "/"
							+ fundTransfer.getTransactionId() +" to : A/c - " + fundTransfer.getReceiverAccountNumber());
				}
				if (fundTransfer.getFundMessage().equals(FundMessage.CREDITED)) {
					statementDto.setNarration(fundTransfer.getFundMessage().toString() + "/"
							+ fundTransfer.getTransactionId() + "/ from A/c - " + fundTransfer.getSenderAccountNumber());
				}
				if (fundTransfer.getFundMessage().equals(FundMessage.CREDITED)) {
					statementDto.setCredit(fundTransfer.getAmount());
				} else {
					statementDto.setDebit(fundTransfer.getAmount());
				}
			} else {
				statementDto.setNarration("TGRE54HU8GTJ" + "/" + fundTransfer.getTransactionId());
			}

		}

		return statementDto;
	}

	/*
	 * This function will create an MS Excel file using the
	 * StatementDto(Transaction).
	 */
	public ByteArrayOutputStream generateExcel(List<StatementDto> statements) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("accountStatement");

			// Creating headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "Date&Time", "Narration", "Debit", "Credit", "Balance" };
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Populate user data
			int rows = 1;
			for (StatementDto statementDto : statements) {
				Row rowData = sheet.createRow(rows);
				for (int cell = 0; cell < headers.length; cell++) {
					Cell cellData = rowData.createCell(cell);
					switch (cell) {
					case 0:
						cellData.setCellValue(statementDto.getDate());
						break;
					case 1:
						cellData.setCellValue(statementDto.getNarration());
						break;
					case 2:
						if (statementDto.getDebit() == 0) {
							cellData.setCellValue("");
						} else {
							cellData.setCellValue(statementDto.getDebit());
						}
						break;
					case 3:
						if (statementDto.getCredit() == 0) {
							cellData.setCellValue("");
						} else {
							cellData.setCellValue(statementDto.getCredit());
						}
						break;
					case 4:
						cellData.setCellValue(statementDto.getBalance());
						break;
					}
				}
				rows++;
			}

			// Write workbook to ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return outputStream;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * This function will create an PDF file using the StatementDto(Transaction).
	 */
	public ByteArrayOutputStream generatePDF(List<StatementDto> statements) {

		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage();
			document.addPage(page);

			// Create content stream
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(80, 700);
			contentStream.showText("Date");
			contentStream.newLineAtOffset(100, 0);
			contentStream.showText("Narration");
			contentStream.newLineAtOffset(120, 0);
			contentStream.showText("Debit");
			contentStream.newLineAtOffset(80, 0);
			contentStream.showText("Credit");
			contentStream.newLineAtOffset(80, 0);
			contentStream.showText("Balance");
			contentStream.endText();

			int y = 680;
			for (StatementDto statementDto : statements) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA, 12);
				contentStream.newLineAtOffset(80, y);
				DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
				contentStream.showText(statementDto.getDate().format(formatter));
				contentStream.newLineAtOffset(100, 0);
				contentStream.showText(statementDto.getNarration());
				contentStream.newLineAtOffset(120, 0);
				contentStream.showText(String.valueOf(statementDto.getDebit()));
				contentStream.newLineAtOffset(80, 0);
				contentStream.showText(String.valueOf(statementDto.getCredit()));
				contentStream.newLineAtOffset(80, 0);
				contentStream.showText(String.valueOf(statementDto.getBalance()));
				contentStream.endText();
				y -= 20;
			}

			contentStream.close();

			// Write document to ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			document.save(outputStream);
			return outputStream;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
