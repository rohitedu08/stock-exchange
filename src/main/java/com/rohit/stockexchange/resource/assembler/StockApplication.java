package com.rohit.stockexchange.resource.assembler;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rohit.stockexchange.resources.viewobjects.CreateStockVo;
import com.rohit.stockexchange.resources.viewobjects.StockVo;
import com.rohit.stockexchange.resources.viewobjects.UpdateStockVo;

/**
 * This is an abstraction which provides response from the Domain Model and
 * taked care of the Response Entity creation for various stock service
 * Operations
 * 
 * @author Rohit Sharma
 *
 */
public interface StockApplication {

	/**
	 * Retrieve all the stocks from the store and forms a response entity
	 * 
	 * @return Response entity with http status
	 */
	public ResponseEntity<List<StockVo>> getStocks();

	/**
	 * Retrieve a single stock based on provided id and forms a response entity
	 * 
	 * @param id
	 * @return Response entity with http status
	 */
	public ResponseEntity<StockVo> getStock(final Long id);

	/**
	 * Updates the stock with Provided price. Price is made using Java Money API
	 * 
	 * @param id
	 * @param updateStockVo
	 * @return Response entity with http status
	 */
	public ResponseEntity<StockVo> updateStock(Long id, UpdateStockVo updateStockVo);

	/**
	 * Creates the Stock based on input request.
	 * 
	 * @param createStockVo
	 * @return Response entity with http status
	 */
	public ResponseEntity<StockVo> createStock(CreateStockVo createStockVo);

}
