package com.rohit.stockexchange.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.stockexchange.application.StockApplication;
import com.rohit.stockexchange.resources.viewobjects.CreateStockVo;
import com.rohit.stockexchange.resources.viewobjects.StockVo;
import com.rohit.stockexchange.resources.viewobjects.UpdateStockVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Spring-boot controller to handle CRUD operations on stocks
 * 
 * @author Rohit Sharma
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = { "Stock Exchange" })
public class StockController {

	@Autowired
	private StockApplication stockApplication;

	@ApiOperation(value = "provides list of all the available stocks", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of stocks"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping(value = "stocks", produces = "application/json")
	public ResponseEntity<List<StockVo>> getStocks() {
		return stockApplication.getStocks();
	}

	@ApiOperation(value = "retrieve single stock based on id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the stock"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping(value = "stocks/{id}", produces = "application/json")
	public ResponseEntity<StockVo> getStock(@PathVariable("id") Long id) {

		return stockApplication.getStock(id);
	}

	@ApiOperation(value = "updates the provided stock into the store", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated the stock"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 422, message = "Stock data is not valid, can not be updated"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PutMapping(value = "stocks/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<StockVo> updateStock(@PathVariable("id") Long id,
			@Valid @RequestBody UpdateStockVo updateStockVo) {

		return stockApplication.updateStock(id, updateStockVo);
	}

	@ApiOperation(value = "creates a new stock into the store", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created the stock"),
			@ApiResponse(code = 422, message = "Stock data is not valid, can not be updated"),
			@ApiResponse(code = 409, message = "Stock with the given data is already available"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping(value = "stocks", produces = "application/json", consumes = "application/json")
	public ResponseEntity<StockVo> createStock(@Valid @RequestBody CreateStockVo createStockVo) {

		return stockApplication.createStock(createStockVo);
	}

}
