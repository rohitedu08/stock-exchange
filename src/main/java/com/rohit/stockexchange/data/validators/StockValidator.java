package com.rohit.stockexchange.data.validators;

import com.rohit.stockexchange.models.Stock;

/**
 * This interface can be implemented for the purpose of validating the fields on
 * stock entity. at this moment, only stock name and stock current price
 * validators implements itFs
 * 
 * @author Rohit Sharma
 *
 */
public interface StockValidator {

	public boolean isValid(Stock stock);

}
