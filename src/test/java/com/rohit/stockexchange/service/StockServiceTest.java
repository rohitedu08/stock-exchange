package com.rohit.stockexchange.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rohit.stockexchange.error.StockExchangeException;
import com.rohit.stockexchange.models.Stock;
import com.rohit.stockexchange.repository.StockRepository;
import com.rohit.stockexchange.validators.StockValidatorManager;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

	@Mock
	private StockRepository stockRepository;

	@Mock
	private StockValidatorManager stockValidator;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private List<Stock> stocks;

	@InjectMocks
	private StockServiceImpl stockService;

	@Before
	public void init() {

		Money money = Money.parse("USD 234");
		stocks = Arrays.asList(new Stock("ABN Amro", money), new Stock("ING", money));

	}

	@Test
	public void testGetAllStocks() {

		Mockito.when(stockRepository.findAll()).thenReturn(stocks);
		List<Stock> stocksList = stockService.getStocks();
		Assert.assertEquals(2, stocksList.size());
		Assert.assertTrue(
				stocksList.stream().filter(stock -> stock.getName().equals("ABN Amro")).findAny().isPresent());

	}

	@Test
	public void testGetSinglestockHappyFLow() {
		Long id = Long.valueOf(1234);

		Optional<Stock> optionalStock = Optional.of(stocks.get(0));
		Mockito.when(stockRepository.findById(id)).thenReturn(optionalStock);
		Stock stock = stockService.getStock(id);
		Assert.assertEquals("ABN Amro", stock.getName());
		Assert.assertEquals("USD 234.00", stock.getCurrentPrice().toString());
	}

	@Test
	public void givenNullStockId_whenCallGetStock_thenReturnNotFound() {
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Stock not found");
		stockService.getStock(null);
	}

	@Test
	public void givenNonExistentStockId_whenCallGetStock_thenReturnNotFound() {
		Long id = Long.valueOf(1234);
		Optional<Stock> optionalStock = Optional.empty();
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Given Stock not found in the store");
		Mockito.when(stockRepository.findById(id)).thenReturn(optionalStock);
		stockService.getStock(id);
	}

	@Test
	public void testUpdateStockHappyFlow() {
		Long id = Long.valueOf(1234);
		Stock stockUpdates = new Stock("ABN Amro", Money.parse("USD 543"));
		Optional<Stock> optionalExistingStock = Optional.of(stocks.get(0));
		Mockito.when(stockValidator.isValid(Mockito.any(Stock.class))).thenReturn(true);
		Mockito.when(stockRepository.findById(id)).thenReturn(optionalExistingStock);
		Mockito.when(stockRepository.save(stocks.get(0))).thenReturn(stocks.get(0));
		Stock stock = stockService.updateStock(id, stockUpdates);
		Assert.assertEquals("USD 543.00", stock.getCurrentPrice().toString());
		Assert.assertEquals("ABN Amro", stock.getName());
	}

	@Test
	public void testUpdateAgainstNonExistentStock() {
		Long id = Long.valueOf(1234);
		Stock stockUpdates = new Stock("ABN Amro", Money.parse("USD 543"));
		Optional<Stock> optionalNonExistingStock = Optional.empty();
		Mockito.when(stockValidator.isValid(Mockito.any(Stock.class))).thenReturn(true);
		Mockito.when(stockRepository.findById(id)).thenReturn(optionalNonExistingStock);
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Given Stock not found in the store");
		stockService.updateStock(id, stockUpdates);

	}

	@Test
	public void givenNullStockId_whenCallUpdateStock_thenReturnNotFound() {
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Stock not found");
		stockService.updateStock(null, stocks.get(0));

	}

	@Test
	public void givenStockWithInvalidData_whenCallUpdateStock_thenReturnDataNotValid() {
		Long id = Long.valueOf(1234);
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Stock data is not valid, can not be updated");
		Mockito.when(stockValidator.isValid(Mockito.any(Stock.class))).thenReturn(false);
		stockService.updateStock(id, stocks.get(0));

	}

	@Test
	public void testcreateStockHappyFlow() {
		Stock stockToBeCreated = new Stock("ABN Amro", Money.parse("USD 123"));
		Mockito.when(stockValidator.isValid(Mockito.any(Stock.class))).thenReturn(true);
		Mockito.when(stockRepository.findByNameIgnoreCase(stockToBeCreated.getName())).thenReturn(null);
		Mockito.when(stockRepository.save(Mockito.any(Stock.class))).thenReturn(stockToBeCreated);
		final Stock createdStock = stockService.createStock(stockToBeCreated);

		Assert.assertEquals("ABN Amro", createdStock.getName());
		Assert.assertEquals("USD 123.00", createdStock.getCurrentPrice().toString());
	}

	@Test
	public void givenAstockWithInvalidData_whenCallCreateStock_thenReturnDataNotValid() {
		Stock stockToBeCreated = new Stock("ABN Amro", Money.parse("USD 123"));
		Mockito.when(stockValidator.isValid(Mockito.any(Stock.class))).thenReturn(false);
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Provided stock contains invalid data, can not be created");
		stockService.createStock(stockToBeCreated);

	}

	@Test
	public void givenAstockAlreadyExist_whenCallCreateStock_thenReturnDataNotValid() {
		Stock stockToBeCreated = new Stock("ABN Amro", Money.parse("USD 123"));
		Mockito.when(stockValidator.isValid(Mockito.any(Stock.class))).thenReturn(true);
		Mockito.when(stockRepository.findByNameIgnoreCase(stockToBeCreated.getName())).thenReturn(stockToBeCreated);
		thrown.expect(StockExchangeException.class);
		thrown.expectMessage("Stock with the given data is already available");
		stockService.createStock(stockToBeCreated);

	}

}
