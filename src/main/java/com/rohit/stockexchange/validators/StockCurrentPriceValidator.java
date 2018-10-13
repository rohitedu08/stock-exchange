package com.rohit.stockexchange.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rohit.stockexchange.model.Stock;

@Component
public class StockCurrentPriceValidator implements StockValidator<Stock> {
	private static Logger log = LoggerFactory.getLogger(StockCurrentPriceValidator.class);

	@Override
	public boolean isValid(Stock stock) {
		if (null == stock.getCurrentPrice()) {
			log.warn("Current price of the stock is null");
			return false;
		}
		return true;
	}

}
