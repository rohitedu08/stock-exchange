package com.rohit.stockexchange.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class will catch all the exceptions thrown from the Stock exchange app.
 * It handles specific exceptions such as StockExchangeException which is thrown
 * for some specific erroneous scenarios. MethodArgumentNotValidException
 * handles when an input validation is failed. Rest all exceptions will be in
 * generic category and will return HTTP Status 400.
 * 
 * @author Rohit Sharma
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(final Exception ex) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("Validation error");
		errorResponse.setMessage(ex.getMessage());
		if (ex instanceof StockExchangeException) {
			httpStatus = ((StockExchangeException) ex).getStatus();
			errorResponse.setErrorCode(((StockExchangeException) ex).getCode());
		} else if (ex instanceof MethodArgumentNotValidException) {
			errorResponse.setMessage(
					((MethodArgumentNotValidException) ex).getBindingResult().getFieldError().getDefaultMessage());
		}

		return new ResponseEntity<ErrorResponse>(errorResponse, httpStatus);
	}
}