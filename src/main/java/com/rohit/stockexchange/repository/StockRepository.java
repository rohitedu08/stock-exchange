package com.rohit.stockexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.stockexchange.models.Stock;

/**
 * Repository to manage operations on stock instances
 * 
 * @author RTSHARMA
 *
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	/**
	 * A case insensitive retrieval of the stock by its name.
	 * 
	 * @param name
	 * @return Stock Object.
	 */
	public Stock findByNameIgnoreCase(String name);

}
