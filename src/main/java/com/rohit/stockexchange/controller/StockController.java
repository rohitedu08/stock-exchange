package com.rohit.stockexchange.controller;

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

/**
 * Spring-boot controller to handle CRUD operations on stocks
 * 
 * @author Rohit Sharma
 *
 */
@RestController
@RequestMapping("/api")
public class StockController {

	@Autowired
	private StockApplication stockApplication;

	@GetMapping("stocks")
	public ResponseEntity<List<StockVo>> getStocks() {
		return stockApplication.getStocks();
	}

	@GetMapping("stocks/{id}")
	public ResponseEntity<StockVo> getStock(@PathVariable("id") Long id) {

		return stockApplication.getStock(id);
	}

	@PutMapping("stocks/{id}")
	public ResponseEntity<StockVo> updateStock(@PathVariable("id") Long id,
			@Valid @RequestBody UpdateStockVo updateStockVo) {

		return stockApplication.updateStock(id, updateStockVo);
	}

	@PostMapping("stocks")
	public ResponseEntity<StockVo> createStock(@Valid @RequestBody CreateStockVo createStockVo) {

		return stockApplication.createStock(createStockVo);
	}

}
