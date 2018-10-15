package com.rohit.stockexchange.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(final Exception ex) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("Validation error");
		if (ex instanceof StockExchangeException) {
			httpStatus = ((StockExchangeException) ex).getStatus();
			errorResponse.setErrorCode(((StockExchangeException) ex).getCode());
		}
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, httpStatus);
	}
}