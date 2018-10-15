package com.rohit.stockexchange.input.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.money.Money;

/**
 * validate the provided string against Joda money standards.
 * 
 * @author Rohit Sharma
 *
 */
class CurrentPriceValidator implements ConstraintValidator<ValidCurrentPrice, String> {

	@Override
	public boolean isValid(String currentPrice, ConstraintValidatorContext arg1) {
		try {
			Money.parse(currentPrice);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
