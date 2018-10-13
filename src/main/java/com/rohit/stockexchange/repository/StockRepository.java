package com.rohit.stockexchange.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rohit.stockexchange.model.Stock;

/**
 * Repository to manage operations on stock instances
 * 
 * @author RTSHARMA
 *
 */
@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

	/**
	 * Get a stock by it's name.
	 * 
	 * @param name
	 * @return Stock Object.
	 */
	public Stock findByNameIgnoreCase(String name);

}
