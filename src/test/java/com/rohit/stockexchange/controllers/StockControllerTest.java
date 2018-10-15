package com.rohit.stockexchange.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.joda.money.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohit.stockexchange.application.StockApplication;
import com.rohit.stockexchange.error.StockExchangeException;
import com.rohit.stockexchange.models.Stock;
import com.rohit.stockexchange.resources.viewobjects.CreateStockVo;
import com.rohit.stockexchange.resources.viewobjects.StockVo;
import com.rohit.stockexchange.resources.viewobjects.UpdateStockVo;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private StockApplication stockApplication;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private StockVo stockVo;

	@Before
	public void init() {
		stockVo = new StockVo(new Stock("ABN", Money.parse("USD 23.56")));
		stockVo.setId(Long.valueOf(1234));
		stockVo.setLastUpdate(LocalDateTime.now());
	}

	@Test
	public void givenStocks_whenCallGetStocks_thenReturnJsonArray() throws Exception {

		final List<StockVo> stockVos = Arrays.asList(stockVo);
		final ResponseEntity<List<StockVo>> getStocksResponse = new ResponseEntity<>(stockVos, HttpStatus.OK);
		
		Mockito.when(stockApplication.getStocks()).thenReturn(getStocksResponse);

		mockMvc.perform(get("/api/stocks")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", equalTo(stockVo.getName())))
				.andExpect(jsonPath("$[0].currentPrice", equalTo(stockVo.getCurrentPrice())));
	}
	
	
	@Test
	public void givenStock_whenCallGetStock_thenReturnSingleStock() throws Exception {

		final ResponseEntity<StockVo> getStockResponse = new ResponseEntity<>(stockVo, HttpStatus.OK);
		Mockito.when(stockApplication.getStock(Long.valueOf(1234))).thenReturn(getStockResponse);

		mockMvc.perform(get("/api/stocks/1234")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(4)))
				.andExpect(jsonPath("$.name", equalTo(stockVo.getName())))
				.andExpect(jsonPath("$.currentPrice", equalTo(stockVo.getCurrentPrice())));
	}
	
	@Test
	public void givenStock_whenCallToUpdateStock_thenReturnUpdatedStock() throws Exception {
		final UpdateStockVo updateStockVo= new UpdateStockVo();
		updateStockVo.setCurrentPrice("USD 23.56");
		ObjectMapper objectMapper = new ObjectMapper();
		final ResponseEntity<StockVo> getStockResponse = new ResponseEntity<>(stockVo, HttpStatus.OK);
		
		Mockito.when(stockApplication.updateStock( Mockito.any(Long.class),
		        Mockito.any(UpdateStockVo.class))).thenReturn(getStockResponse);
		
		mockMvc.perform(put("/api/stocks/1234")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(updateStockVo)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.currentPrice", equalTo(stockVo.getCurrentPrice())));
	}
	
	
	@Test
	public void givenStock_whenCallToCreateNewStock_thenReturnCreatedStock() throws Exception {
		final CreateStockVo createStockVo= new CreateStockVo();
		createStockVo.setCurrentPrice("USD 23.56");
		createStockVo.setName("ABN");
		ObjectMapper objectMapper = new ObjectMapper();
		final ResponseEntity<StockVo> getStockResponse = new ResponseEntity<>(stockVo, HttpStatus.CREATED);
		
		Mockito.when(stockApplication.createStock(Mockito.any(CreateStockVo.class))).thenReturn(getStockResponse);
		
		mockMvc.perform(post("/api/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(createStockVo)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", equalTo(stockVo.getId().intValue())))
				.andExpect(jsonPath("$.name", equalTo(createStockVo.getName())))
				.andExpect(jsonPath("$.currentPrice", equalTo(createStockVo.getCurrentPrice())));
	}
	
	
	@Test
	public void givenStockWithMalformedMoney_whenCallToCreateNewStock_thenReturnError() throws Exception {
		final CreateStockVo createStockVo= new CreateStockVo();
		createStockVo.setCurrentPrice("23.56");
		createStockVo.setName("ABN");
		ObjectMapper objectMapper = new ObjectMapper();
		
		mockMvc.perform(post("/api/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(createStockVo)))
		        .andExpect(status().isBadRequest());;
	}
	
	@Test
	public void givenStockWithEmptyName_whenCallToCreateNewStock_thenReturnError() throws Exception {
		final CreateStockVo createStockVo= new CreateStockVo();
		createStockVo.setCurrentPrice("USD 23.56");
		createStockVo.setName("");
		ObjectMapper objectMapper = new ObjectMapper();
		final ResponseEntity<StockVo> getStockResponse = new ResponseEntity<>(stockVo, HttpStatus.CREATED);
		
		Mockito.when(stockApplication.createStock(Mockito.any(CreateStockVo.class))).thenReturn(getStockResponse);
		
		mockMvc.perform(post("/api/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(createStockVo))) .andExpect(status().isBadRequest());
	}
}
