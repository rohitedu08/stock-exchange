package com.rohit.stockexchange.data.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rohit.stockexchange.models.Stock;

/**
 * Validates whether the currentPrice is not null, if it is then returns false
 * 
 * @author Rohit Sharma
 *
 */
@Component
public class StockCurrentPriceValidator implements StockValidator {
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
