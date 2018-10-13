package com.rohit.stockexchange.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(final Exception ex) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getMessage());
		if (ex instanceof StockExchangeException) {
			httpStatus = ((StockExchangeException) ex).getStatus();
			errorResponse.setErrorCode(((StockExchangeException) ex).getCode());
			errorResponse.setMessage(((StockExchangeException) ex).getMessage());
		}

		return new ResponseEntity<ErrorResponse>(errorResponse, httpStatus);
	}
}