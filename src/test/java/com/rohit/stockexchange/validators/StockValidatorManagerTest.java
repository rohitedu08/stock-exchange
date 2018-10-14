package com.rohit.stockexchange.validators;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rohit.stockexchange.models.Stock;

@RunWith(MockitoJUnitRunner.class)
public class StockValidatorManagerTest {

	@Mock
	private StockNameValidator nameValidator;
	@Mock
	private Stock stock;

	@Mock
	private StockCurrentPriceValidator priceValidator;

	private StockValidatorManager validationManager;

	private List<StockValidator> stockValidators;

	@Before
	public void init() {
		stockValidators = Arrays.asList(priceValidator, nameValidator);
		validationManager = new StockValidatorManager(stockValidators);
	}

	@Test
	public void testValidatorWhenNameValidatorFails() {
		Mockito.when(nameValidator.isValid(stock)).thenReturn(false);
		Mockito.when(priceValidator.isValid(stock)).thenReturn(true);
		Assert.assertFalse(validationManager.isValid(stock));
	}

	@Test
	public void testValidatorWithValidStock() {
		Mockito.when(nameValidator.isValid(stock)).thenReturn(true);
		Mockito.when(priceValidator.isValid(stock)).thenReturn(true);
		Assert.assertTrue(validationManager.isValid(stock));
	}

}
