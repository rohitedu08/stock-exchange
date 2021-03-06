package com.rohit.stockexchange.data.validators;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rohit.stockexchange.models.Stock;

/**
 * Manager collects all the field validator corresponding to Stock class and
 * scan Stock through each validator. It checks if one of the validator
 * invalidate the stock and returns
 * 
 * @author Rohit Sharma
 *
 */
@Component
public class StockValidatorManager {

	private List<StockValidator> stockValidators;

	@Autowired
	public StockValidatorManager(final List<StockValidator> stockValidators) {
		this.stockValidators = stockValidators;
	}

	public boolean isValid(Stock stock) {
		Optional<StockValidator> found = stockValidators.stream().filter(validator -> !validator.isValid(stock))
				.findAny();
		if (found.isPresent()) {
			return false;
		}
		return true;
	}
}
