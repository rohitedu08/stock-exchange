package com.rohit.stockexchange.resources.viewobjects;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rohit.stockexchange.models.Stock;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode
public class StockVo {

	private Long id;
	@NotNull
	@Size(min = 2, max = 30)
	private String name;
	@NotNull
	private String currentPrice;

	private LocalDateTime lastUpdate;

	public StockVo(Stock stock) {
		Assert.notNull(stock, "Stock can not be null");
		this.id = stock.getId();
		this.name = stock.getName();
		this.currentPrice = stock.getCurrentPrice().toString();
		this.lastUpdate = stock.getLastUpdate();

	}

}
