package com.rohit.stockexchange.error;

public enum StockApplicationErrorCodes {

	STOCK_NOT_FOUND("STOCK_0001"), STOCK_ALREADY_AVAILABLE("STOCK_0002"), INVALID_STOCK_DATA("STOCK_00043");

	private String code;

	private StockApplicationErrorCodes(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
