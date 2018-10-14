package com.rohit.stockexchange.validators;

import org.joda.money.Money;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.rohit.stockexchange.error.StockApplicationErrorCodes;
import com.rohit.stockexchange.error.StockExchangeException;

/**
 * A simple Joda Money validator which checks if the input string is in valid
 * format or not.
 * 
 * @author Rohit Sharma
 *
 */
public class MoneyValidator {

	public static void validate(String moneyStr) {
		Assert.isTrue(!StringUtils.isEmpty(moneyStr), "Please provide a valid current price in formate i.e  'EUR 23'");
		try {
			Money.parse(moneyStr);
		} catch (Exception e) {
			throw new StockExchangeException(HttpStatus.UNPROCESSABLE_ENTITY,
					StockApplicationErrorCodes.INVALID_STOCK_DATA.getCode(),
					moneyStr + " is invalid format, Use valid currency format such as  'USD 543' ");
		}
	}
}
