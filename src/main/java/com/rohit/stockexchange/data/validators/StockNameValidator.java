package com.rohit.stockexchange.data.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rohit.stockexchange.models.Stock;

/**
 * Validates whether the stock name is not empty and null
 * 
 * @author Rohit Sharma
 *
 */
@Component
public class StockNameValidator implements StockValidator {
	private static Logger log = LoggerFactory.getLogger(StockNameValidator.class);

	@Override
	public boolean isValid(Stock stock) {

		if (StringUtils.isEmpty(stock.getName())) {
			log.warn("stock name is empty or null");
			return false;
		}
		return true;
	}

}
