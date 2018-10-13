package com.rohit.stockexchange.validators;

public interface StockValidator<T> {

	public boolean isValid(T t);

}
