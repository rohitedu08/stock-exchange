package com.rohit.stockexchange.resource.assembler;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rohit.stockexchange.model.Stock;
import com.rohit.stockexchange.resources.viewobjects.CreateStockVo;
import com.rohit.stockexchange.resources.viewobjects.StockVo;
import com.rohit.stockexchange.resources.viewobjects.UpdateStockVo;
import com.rohit.stockexchange.service.StockService;

@Component
class StockApplicationImpl implements StockApplication {

	@Autowired
	private StockService stockService;

	@Override
	public ResponseEntity<List<StockVo>> getStocks() {
		List<StockVo> stockVos = new ArrayList<>();
		stockService.getStocks().forEach(stock -> {
			stockVos.add(new StockVo(stock));
		});

		return new ResponseEntity<List<StockVo>>(stockVos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StockVo> getStock(Long id) {
		return new ResponseEntity<StockVo>(new StockVo(stockService.getStock(id)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StockVo> updateStock(Long id, UpdateStockVo updateStockVo) {

		Stock stock = stockService.getStock(id);
		Money money = Money.parse(updateStockVo.getCurrentPrice());
		Stock stockToBeUpdated = new Stock(stock.getName(), money);
		return new ResponseEntity<StockVo>(new StockVo(stockService.updateStock(id, stockToBeUpdated)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StockVo> createStock(CreateStockVo createStockVo) {
		Money currentPrice = Money.parse(createStockVo.getCurrentPrice());
		Stock stockToBeCreated = new Stock(createStockVo.getName(), currentPrice);

		return new ResponseEntity<StockVo>(new StockVo(stockService.createStock(stockToBeCreated)), HttpStatus.CREATED);
	}

}
