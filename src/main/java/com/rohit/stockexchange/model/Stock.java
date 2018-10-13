package com.rohit.stockexchange.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * A Stock entity
 * 
 * @author RTSHARMA
 *
 */
@Data
@Entity
@NoArgsConstructor(force = true)
@EqualsAndHashCode
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private MonetaryAmount currentPrice;

	@CreationTimestamp
	private LocalDateTime created;
	@UpdateTimestamp
	private LocalDateTime lastUpdate;

	public Stock(String name, MonetaryAmount currentPrice) {
		this.name = Objects.requireNonNull(name, " Stock name must be provided");
		this.currentPrice = Objects.requireNonNull(currentPrice, "Stock price must be supplied");

	}

}
