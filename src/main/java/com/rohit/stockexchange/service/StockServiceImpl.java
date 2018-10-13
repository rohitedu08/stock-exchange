package com.rohit.stockexchange.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rohit.stockexchange.model.Stock;
import com.rohit.stockexchange.repository.StockRepository;

@Component
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;

	@Override
	public List<Stock> getStocks() {
		return stockRepository.findAll();
	}

	@Override
	public Optional<Stock> getStock(final Long id) {
		return stockRepository.findById(id);
	}

	@Override
	public Optional<Stock> updateStock(Long id, BigDecimal currentPrice) {
		Optional<Stock> found = stockRepository.findById(id);

		if (found.isPresent()) {
			found.get().setCurrentPrice(currentPrice);
			return Optional.of(stockRepository.save(found.get()));
		}
		return Optional.empty();
	}

	@Override
	public Stock createStock(final Stock stock) {
		return stockRepository.save(stock);
	}

}
