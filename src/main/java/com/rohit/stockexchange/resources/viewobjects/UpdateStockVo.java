package com.rohit.stockexchange.resources.viewobjects;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UpdateStockVo {

	private String currentPrice;

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		Assert.isTrue(!StringUtils.isEmpty(currentPrice),
				"Please provide a valid current price in formate i.e  EUR 23");
		this.currentPrice = currentPrice;
	}

}
