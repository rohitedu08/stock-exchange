package com.rohit.stockexchange.service;

import java.util.List;
import java.util.Optional;

import com.rohit.stockexchange.model.Stock;

/**
 * Interface to perform stock services
 * 
 * @author RTSHARMA
 *
 */
public interface StockService {

	/**
	 * Get the list of all the stocks present in the store
	 * 
	 * @return List of retrieved stocks
	 */
	List<Stock> getStocks();

	/**
	 * Get a single stock from the store using the provided id
	 * 
	 * @param id
	 * @return retrieved stock
	 */
	Optional<Stock> getStock(final Long id);

	/**
	 * Updates the current price of existing stock with the provided one
	 * 
	 * @param id
	 * @param replace
	 * @return Updated Stock
	 */
	Optional<Stock> updateStock(final Long id, final Stock update);

	/**
	 * Creates a new Stock in the store
	 * 
	 * @param stock
	 * @return Created Stock
	 */
	Stock createStock(final Stock stock);

}
