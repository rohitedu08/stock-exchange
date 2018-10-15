package com.rohit.stockexchange;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohit.stockexchange.models.Stock;
import com.rohit.stockexchange.repository.StockRepository;
import com.rohit.stockexchange.resources.viewobjects.CreateStockVo;
import com.rohit.stockexchange.resources.viewobjects.StockVo;
import com.rohit.stockexchange.resources.viewobjects.UpdateStockVo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockExchangeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class StockExchangeApplicationIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private StockRepository repository;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	private static boolean stockCreated = false;

	private ObjectMapper mapper;

	@Before
	public void init() {
		mapper = new ObjectMapper();
		createStock(new Stock("ABN Amro", Money.parse("USD 11.12")));
	}

	@Test
	public void givenStocks_whenAllStocksInfoIsRetrieved_then200IsReceived() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks"), HttpMethod.GET, entity,
				String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void givenStocks_whenAllStocksAreRetrieved_thenRetrievedStocksAreCorrect() throws IOException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks"), HttpMethod.GET, entity,
				String.class);

		List<StockVo> stocks = Arrays.asList(mapper.readValue(response.getBody(), StockVo[].class));

		List<Integer> abnStockCount = stocks.stream().filter(s -> "ABN Amro".equals(s.getName())).map(i -> 1)
				.collect(Collectors.toList());
		Assert.assertEquals(1, abnStockCount.size());
		Assert.assertEquals("ABN Amro", stocks.get(0).getName());
		Assert.assertEquals("USD 11.12", stocks.get(0).getCurrentPrice());
	}

	@Test
	public void givenSingleStock_whenStockInfoIsRetrieved_then200IsReceived() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/1"), HttpMethod.GET,
				entity, String.class);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void givenStockDoesNotExists_whenStockInfoIsRetrieved_then404IsReceived() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/20"), HttpMethod.GET,
				entity, String.class);

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void givenSingleStocks_whenStockInfoIsRetrieved_thenRetrievedStockIsCorrect() throws IOException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/1"), HttpMethod.GET,
				entity, String.class);
		StockVo stock = mapper.readValue(response.getBody(), StockVo.class);
		Assert.assertEquals("ABN Amro", stock.getName());
		Assert.assertEquals("USD 11.12", stock.getCurrentPrice());
	}

	@Test
	public void givenRequestToUpdateNonExistentStock_whenCallUpdateStock_then404Receieved() throws IOException {
		final UpdateStockVo updateStockVo = new UpdateStockVo();
		updateStockVo.setCurrentPrice("INR 30");
		HttpEntity<UpdateStockVo> entity = new HttpEntity<UpdateStockVo>(updateStockVo, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/10"), HttpMethod.PUT,
				entity, String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void givenRequestToUpdateStock_whenCallUpdateStock_thenRetrievedStockIsCorrect() throws IOException {

		final UpdateStockVo updateStockVo = new UpdateStockVo();
		updateStockVo.setCurrentPrice("INR 30.10");
		HttpEntity<UpdateStockVo> entity = new HttpEntity<UpdateStockVo>(updateStockVo, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/1"), HttpMethod.PUT,
				entity, String.class);
		StockVo stock = mapper.readValue(response.getBody(), StockVo.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("ABN Amro", stock.getName());
		Assert.assertEquals("INR 30.10", stock.getCurrentPrice());
	}

	@Test
	public void givenRequestToCreateStock_whenCallCreateStock_then201IsReceived() throws IOException {

		final CreateStockVo createStockVo = new CreateStockVo();
		createStockVo.setCurrentPrice("INR 30.10");
		createStockVo.setName("Google");
		HttpEntity<CreateStockVo> entity = new HttpEntity<CreateStockVo>(createStockVo, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks"), HttpMethod.POST,
				entity, String.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void givenRequestToCreateStock_whenCallCreateStock_thenStockIsCreated() throws IOException {
		final CreateStockVo createStockVo = new CreateStockVo();
		createStockVo.setCurrentPrice("INR 30.10");
		createStockVo.setName("Facebook");
		HttpEntity<CreateStockVo> entity = new HttpEntity<CreateStockVo>(createStockVo, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks"), HttpMethod.POST,
				entity, String.class);
		StockVo stock = mapper.readValue(response.getBody(), StockVo.class);
		Assert.assertEquals("Facebook", stock.getName());
		Assert.assertEquals("INR 30.10", stock.getCurrentPrice());
	}

	@Test
	public void givenRequestToCreateExistingStock_whenCallCreateStock_then409IsReceived() throws IOException {
		final CreateStockVo createStockVo = new CreateStockVo();
		createStockVo.setCurrentPrice("INR 30.10");
		createStockVo.setName("ABN Amro");
		HttpEntity<CreateStockVo> entity = new HttpEntity<CreateStockVo>(createStockVo, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks"), HttpMethod.POST,
				entity, String.class);
		Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void givenRequestToCreateStockWithEmptyName_thenError400IsReceieved() throws IOException {
		final CreateStockVo createStockVo = new CreateStockVo();
		createStockVo.setCurrentPrice("INR 30.10");
		createStockVo.setName("");
		HttpEntity<CreateStockVo> entity = new HttpEntity<CreateStockVo>(createStockVo, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks"), HttpMethod.POST,
				entity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void givenRequestToCreateStockWithInvalidPrice_thenError400IsReceieved() throws IOException {
		final CreateStockVo createStockVo = new CreateStockVo();
		createStockVo.setCurrentPrice("30.10");
		createStockVo.setName("Dell");
		HttpEntity<CreateStockVo> entity = new HttpEntity<CreateStockVo>(createStockVo, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks"), HttpMethod.POST,
				entity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private void createStock(Stock stock) {
		if (!stockCreated) {
			repository.save(stock);
			stockCreated = true;
		}
	}

	@Test
	public void contextLoads() {
	}

}
