package com.rohit.stockexchange.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.rohit.stockexchange.models.Stock;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockRepositoryTest {

	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void whenCallFingById_thenReturnStock() {
		Money money = Money.parse("USD 123");
		Stock stock = new Stock("ABN Amro", money);
		testEntityManager.persistAndFlush(stock);
		Stock stockFound = stockRepository.findByNameIgnoreCase(stock.getName());
		Assert.assertSame(stock, stockFound);
	}

	@Test
	public void whenCallFindAll_thenReturnAllStocks() {
		Money money = Money.parse("USD 123");
		Stock abnStock = new Stock("ABN Amro", money);
		Stock ingStock = new Stock("ING", money);
		testEntityManager.persistAndFlush(abnStock);
		testEntityManager.persistAndFlush(ingStock);
		List<Stock> stockList = stockRepository.findAll();
		List<Integer> abnStockCount = stockList.stream()
				.filter(stock -> stock.getName().equals("ABN Amro") || stock.getName().equals("ING")).map(i -> 1)
				.collect(Collectors.toList());
		Assert.assertEquals(2, abnStockCount.size());
	}

	@Test
	public void whenCallSave_thenCreateNewStock() {
		Money money = Money.parse("USD 123");
		Stock stock = new Stock("ABN Amro", money);
		stockRepository.save(stock);
		Assert.assertSame(stock, stockRepository.findByNameIgnoreCase(stock.getName()));

	}

	@Test
	public void whenCallFindByNameIgnoreCase_thenReturnStockWithThatName() {
		Money money = Money.parse("USD 123");
		Stock stock = new Stock("ABN Amro", money);
		testEntityManager.persistAndFlush(stock);
		Stock foundStock = stockRepository.findByNameIgnoreCase("abn amro");
		Assert.assertSame(stock, foundStock);

	}

}
