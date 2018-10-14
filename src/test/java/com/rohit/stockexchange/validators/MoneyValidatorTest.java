package com.rohit.stockexchange.validators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.rohit.stockexchange.error.StockExchangeException;

@RunWith(MockitoJUnitRunner.class)
public class MoneyValidatorTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testValidatorWithEmptyString() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Please provide a valid current price in formate");
		MoneyValidator.validate("");

	}

	@Test
	public void testValidatorWithInvalidMoneyFormat() {
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Use valid currency format");
		MoneyValidator.validate("USD");

	}

}
