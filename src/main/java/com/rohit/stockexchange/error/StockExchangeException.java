package com.rohit.stockexchange.error;

import org.springframework.http.HttpStatus;

public class StockExchangeException extends RuntimeException {

	private static final long serialVersionUID = -7360940660712215283L;
	private final HttpStatus status;
	private final String code;

	public StockExchangeException(final HttpStatus status, final String code, final String message) {
		super(message);
		this.status = status;
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

}
