package com.rohit.stockexchange.resources.viewobjects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rohit.stockexchange.validators.MoneyValidator;

@JsonInclude(Include.NON_NULL)
public class UpdateStockVo {

	@NotNull
	private String currentPrice;

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		MoneyValidator.validate(currentPrice);
		this.currentPrice = currentPrice;
	}

}
