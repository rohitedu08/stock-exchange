package com.rohit.stockexchange.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rohit.stockexchange.models.Stock;

@Component
public class StockNameValidator implements StockValidator<Stock> {
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
