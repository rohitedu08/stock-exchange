package com.rohit.stockexchange.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.rohit.stockexchange.error.StockApplicationErrorCodes;
import com.rohit.stockexchange.error.StockExchangeException;
import com.rohit.stockexchange.model.Stock;
import com.rohit.stockexchange.repository.StockRepository;
import com.rohit.stockexchange.validators.StockValidatorManager;

@Component
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private StockValidatorManager stockValidator;

	@Override
	public List<Stock> getStocks() {
		return stockRepository.findAll();
	}

	@Override
	public Stock getStock(@NotNull final Long id) {
		if (null == id) {
			throw new StockExchangeException(HttpStatus.NOT_FOUND, StockApplicationErrorCodes.STOCK_NOT_FOUND.getCode(),
					"Stock not found");
		}
		Optional<Stock> stockResult = stockRepository.findById(id);

		if (!stockResult.isPresent()) {
			throw new StockExchangeException(HttpStatus.NOT_FOUND, StockApplicationErrorCodes.STOCK_NOT_FOUND.getCode(),
					"Given Stock not found in the store");
		}

		return stockResult.get();
	}

	@Override
	public Stock updateStock(Long id, Stock stock) {
		if (null == id) {
			throw new StockExchangeException(HttpStatus.NOT_FOUND, StockApplicationErrorCodes.STOCK_NOT_FOUND.getCode(),
					"Stock not found");
		}

		// Check whether stock contains valid name and Current price

		if (!stockValidator.isValid(stock)) {
			throw new StockExchangeException(HttpStatus.UNPROCESSABLE_ENTITY,
					StockApplicationErrorCodes.INVALID_STOCK_DATA.getCode(),
					"Stock data is not valid, can not be updated");
		}

		Optional<Stock> stockResult = stockRepository.findById(id);

		if (!stockResult.isPresent()) {
			throw new StockExchangeException(HttpStatus.NOT_FOUND, StockApplicationErrorCodes.STOCK_NOT_FOUND.getCode(),
					"Given Stock not found in the store");
		}
		final Stock existingStock = stockResult.get();
		existingStock.setCurrentPrice(stock.getCurrentPrice());
		return stockRepository.save(existingStock);
	}

	@Override
	public Stock createStock(final Stock stock) {

		// Check whether stock contains valid name and Current price
		if (!stockValidator.isValid(stock)) {
			throw new StockExchangeException(HttpStatus.UNPROCESSABLE_ENTITY,
					StockApplicationErrorCodes.INVALID_STOCK_DATA.getCode(),
					"Provided stock contains invalid data, can not be created");
		}

		final Stock availableStock = stockRepository.findByNameIgnoreCase(stock.getName());

		if (null != availableStock) {
			throw new StockExchangeException(HttpStatus.CONFLICT,
					StockApplicationErrorCodes.STOCK_ALREADY_AVAILABLE.getCode(),
					"Stock with the given data is already available");
		}
		return stockRepository.save(stock);

	}

}
