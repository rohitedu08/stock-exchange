package com.rohit.stockexchange.application;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rohit.stockexchange.models.Stock;
import com.rohit.stockexchange.resources.viewobjects.CreateStockVo;
import com.rohit.stockexchange.resources.viewobjects.StockVo;
import com.rohit.stockexchange.resources.viewobjects.UpdateStockVo;
import com.rohit.stockexchange.service.StockService;

@RunWith(MockitoJUnitRunner.class)
public class StockApplicationTest {

	@Mock
	private StockService stockService;

	@Mock
	private Stock stock;

	private List<Stock> stocks;

	@InjectMocks
	private StockApplicationImpl stockApplication;

	@Before
	public void init() {
		Money money = Money.parse("USD 234");
		stocks = Arrays.asList(new Stock("ABN Amro", money), new Stock("ING", money));
	}

	@Test
	public void testGetStocks() {

		Mockito.when(stockService.getStocks()).thenReturn(stocks);
		ResponseEntity<List<StockVo>> response = stockApplication.getStocks();
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(2, response.getBody().size());
		Assert.assertTrue(
				response.getBody().stream().filter(stock -> stock.getName().equals("ING")).findAny().isPresent());
	}

	@Test
	public void testGetStock() {
		Long id = Long.valueOf(1234);
		Mockito.when(stock.getId()).thenReturn(id);
		Mockito.when(stock.getName()).thenReturn("ABN Amro");
		Mockito.when(stock.getCurrentPrice()).thenReturn(Money.parse("USD 234"));
		Mockito.when(stock.getLastUpdate()).thenReturn(LocalDateTime.now());
		Mockito.when(stockService.getStock(id)).thenReturn(stock);
		ResponseEntity<StockVo> response = stockApplication.getStock(id);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("ABN Amro", response.getBody().getName());
		Assert.assertEquals("USD 234.00", response.getBody().getCurrentPrice());
	}

	@Test
	public void tesUpdateStock() {
		Long id = Long.valueOf(1234);
		UpdateStockVo updateStockVo = new UpdateStockVo();
		updateStockVo.setCurrentPrice("USD 234");
		Mockito.when(stockService.getStock(id)).thenReturn(stocks.get(0));
		Mockito.when(stockService.updateStock(Mockito.any(Long.class), Mockito.any(Stock.class)))
				.thenReturn(stocks.get(0));
		ResponseEntity<StockVo> response = stockApplication.updateStock(id, updateStockVo);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("ABN Amro", response.getBody().getName());
		Assert.assertEquals("USD 234.00", response.getBody().getCurrentPrice());
	}

	@Test
	public void tesCreateStock() {
		Long id = Long.valueOf(1234);
		CreateStockVo createStockVo = new CreateStockVo();
		createStockVo.setCurrentPrice("USD 234");
		createStockVo.setName("ABN Amro");
		Mockito.when(stockService.createStock(Mockito.any(Stock.class))).thenReturn(stocks.get(0));
		ResponseEntity<StockVo> response = stockApplication.createStock(createStockVo);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertEquals("ABN Amro", response.getBody().getName());
		Assert.assertEquals("USD 234.00", response.getBody().getCurrentPrice());
	}

}
