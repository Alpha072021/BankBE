package com.alpha.bankApp.entity.idgenerator;

import org.springframework.stereotype.Component;

@Component
public class DebitCardIdGenerator {

	public String debitCardIdGenerator(String bankId, String accountId) {
		String debitCardId = bankId.substring(4) + accountId.substring(7);
		debitCardId += luhnAlgorithm(debitCardId);
		return debitCardId;
	}

	private int luhnAlgorithm(String cardNumber) {
		int sum = 0;
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			int digit = Integer.parseInt(cardNumber.substring(i, i + 1));

			if ((cardNumber.length() - i) % 2 == 0) {
				digit = doubleAndSumDigits(digit);
			}

			sum += digit;
		}

		return sum;
	}

	private int doubleAndSumDigits(int digit) {
		int ret = digit * 2;

		if (ret > 9) {
			ret = digit - 9;
		}

		return ret;
	}
}
