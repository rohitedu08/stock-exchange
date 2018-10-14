package com.rohit.stockexchange.validators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rohit.stockexchange.models.Stock;

@RunWith(MockitoJUnitRunner.class)
public class StockNameValidatorTest {

	@Mock
	private Stock stock;

	@InjectMocks
	private StockNameValidator validator;

	@Test
	public void testValidatorWithEmptyName() {
		Mockito.when(stock.getName()).thenReturn("");
		Assert.assertFalse(validator.isValid(stock));
	}

}
