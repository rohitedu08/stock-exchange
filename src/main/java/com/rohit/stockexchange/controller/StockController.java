package com.rohit.stockexchange.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.stockexchange.model.Stock;
import com.rohit.stockexchange.service.StockService;

@RestController
@RequestMapping("/api")
public class StockController {
	private static Logger log = LoggerFactory.getLogger(StockController.class);

	@Autowired
	private StockService stockService;

	@GetMapping
	HttpEntity<?> getStocks() {
		return new ResponseEntity<List<Stock>>(stockService.getAllStocks(), HttpStatus.OK);
	}

	@GetMapping("stocks/{id}")
	HttpEntity<?> getStock(@PathVariable("id") Long id) {
		return new ResponseEntity<Stock>(stockService.getStock(id), HttpStatus.OK);
	}

	@PutMapping("stocks/{id}")
	HttpEntity<?> updateStock(@PathVariable("id") Long id, BigDecimal currentPrice) {
		return new ResponseEntity<Stock>(stockService.updateStock(id, currentPrice), HttpStatus.OK);
	}

	@PostMapping
	HttpEntity<?> createStock(final Stock stock) {
		return new ResponseEntity<Stock>(stockService.createStock(stock), HttpStatus.OK);
	}

}
