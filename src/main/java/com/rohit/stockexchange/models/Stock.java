package com.rohit.stockexchange.models;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

/**
 * A Stock entity
 * 
 * @author Rohit Sharma
 *
 */
@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 2, max = 30)
	private String name;

	@Columns(columns = { @Column(name = "currency"), @Column(name = "amount") })
	@Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
	private Money currentPrice;

	@UpdateTimestamp
	private LocalDateTime lastUpdate;

	protected Stock() {

	}

	public Stock(String name, Money currentPrice) {
		this.name = Objects.requireNonNull(name, "Stock name must be provided");
		this.currentPrice = Objects.requireNonNull(currentPrice, "Stock current price must be supplied");
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Money getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Money currentPrice) {
		this.currentPrice = Objects.requireNonNull(currentPrice);
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

}
