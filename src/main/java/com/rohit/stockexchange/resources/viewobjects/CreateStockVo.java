package com.rohit.stockexchange.resources.viewobjects;

import java.util.Objects;

import javax.validation.constraints.Size;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rohit.stockexchange.validators.MoneyValidator;

@JsonInclude(Include.NON_NULL)
public class CreateStockVo {

	@Size(min = 2, max = 30)
	private String name;

	private String currentPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name, "Stock name can not be null, please provide Stock name");
	}

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
