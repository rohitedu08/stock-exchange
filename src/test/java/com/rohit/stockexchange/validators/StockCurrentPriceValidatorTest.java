package com.rohit.stockexchange.validators;

import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rohit.stockexchange.models.Stock;

@RunWith(MockitoJUnitRunner.class)
public class StockCurrentPriceValidatorTest {

	@Mock
	private Stock stock;

	@InjectMocks
	private StockCurrentPriceValidator validator;

	@Test
	public void testValidatorWithValidCurrentPrice() {
		Mockito.when(stock.getCurrentPrice()).thenReturn(Money.parse("USD 23"));
		Assert.assertTrue(validator.isValid(stock));
	}

	@Test
	public void testValidatorWithNullCurrentPrice() {
		Mockito.when(stock.getCurrentPrice()).thenReturn(null);
		Assert.assertFalse(validator.isValid(stock));
	}
}
