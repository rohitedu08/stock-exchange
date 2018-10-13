package com.rohit.stockexchange.validators;

import org.joda.money.Money;
import org.springframework.http.HttpStatus;

import com.rohit.stockexchange.error.StockExchangeException;

/**
 * A simple Joda Money validator which checks if the input string is in valid
 * format or not.
 * 
 * @author RTSHARMA
 *
 */
public class MoneyValidator {

	public static void validate(String moneyStr) {

		try {
			Money.parse(moneyStr);
		} catch (Exception e) {
			throw new StockExchangeException(HttpStatus.INTERNAL_SERVER_ERROR, "",
					"Money " + moneyStr + "cannot be parsed, Use example i.e 'USD 543' ");
		}

	}

}
