package com.rohit.stockexchange.resources.viewobjects;

import java.util.Objects;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rohit.stockexchange.input.validators.ValidCurrentPrice;

@JsonInclude(Include.NON_NULL)
public class CreateStockVo {

	@Size(min = 2, max = 30, message = "Please enter name between {min} and {max} characters.")
	private String name;
	@ValidCurrentPrice
	private String currentPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name.trim(), "Stock name can not be null, please provide Stock name");
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice.trim();
	}

}
