package com.rohit.stockexchange.resources;

import java.util.Date;

import org.javamoney.moneta.Money;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class StockResource {
	private final String name;
	private final Money currentPrice;
	private final Date lastUpdate;
}
