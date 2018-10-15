package com.rohit.stockexchange.input.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This constraint validates the user input for current price. It should be in a
 * format specified by Joda Money
 * 
 * @author Rohit Sharma
 *
 */
@Documented
@Constraint(validatedBy = CurrentPriceValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrentPrice {
	String message() default "Invalid current price, provide in the format 'USD 23'";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
