package com.rohit.stockexchange.resources.viewobjects;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
		Assert.isTrue(!StringUtils.isEmpty(currentPrice),
				"Please provide a valid current price in formate i.e  EUR 23");
		MoneyValidator.validate(currentPrice);
		this.currentPrice = currentPrice;
	}

}
