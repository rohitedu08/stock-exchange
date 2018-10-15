package com.rohit.stockexchange;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.rohit.stockexchange.resources.viewobjects.CreateStockVo;
import com.rohit.stockexchange.resources.viewobjects.StockVo;
import com.rohit.stockexchange.resources.viewobjects.UpdateStockVo;
import com.rohit.stockexchange.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockExchangeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class StockExchangeApplicationIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private StockService stockService;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	private static Stock abc;
	private static Stock xyz;
	private static boolean isStockCreated = false;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void init() {
		createSomeStocks();
	}

	private void createSomeStocks() {
		if (!isStockCreated) {
			Money money = Money.parse("USD 123.12");
			abc = new Stock("ABC", money);
			xyz = new Stock("XYZ", money);
			abc = stockService.createStock(abc);
			xyz = stockService.createStock(xyz);
			isStockCreated = true;

		}
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

		Optional<StockVo> googleStock = stocks.stream().filter(s -> "ABC".equals(s.getName())).findAny();
		Assert.assertTrue(googleStock.isPresent());
		Assert.assertEquals("ABC", googleStock.get().getName());
		Assert.assertEquals("USD 123.12", googleStock.get().getCurrentPrice());
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
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/2000"), HttpMethod.GET,
				entity, String.class);

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void givenSingleStocks_whenStockInfoIsRetrieved_thenRetrievedStockIsCorrect() throws IOException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/" + abc.getId()),
				HttpMethod.GET, entity, String.class);

		StockVo stock = mapper.readValue(response.getBody(), StockVo.class);
		Assert.assertEquals("ABC", stock.getName());
		Assert.assertEquals("USD 123.12", stock.getCurrentPrice());
	}

	@Test
	public void givenNonExistentStock_whenCallUpdateStock_then404Receieved() throws IOException {
		final UpdateStockVo updateStockVo = new UpdateStockVo();
		updateStockVo.setCurrentPrice("INR 30");

		HttpEntity<UpdateStockVo> entity = new HttpEntity<UpdateStockVo>(updateStockVo, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/2000"), HttpMethod.PUT,
				entity, String.class);

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void givenExistingStock_whenCallUpdateStock_thenStockIsUpdated() throws IOException {

		final UpdateStockVo updateStockVo = new UpdateStockVo();
		updateStockVo.setCurrentPrice("INR 30.10");

		HttpEntity<UpdateStockVo> entity = new HttpEntity<UpdateStockVo>(updateStockVo, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("api/stocks/" + xyz.getId()),
				HttpMethod.PUT, entity, String.class);

		StockVo stock = mapper.readValue(response.getBody(), StockVo.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("XYZ", stock.getName());
		Assert.assertEquals("INR 30.10", stock.getCurrentPrice());
	}

	@Test
	public void givenRequestToCreateStock_whenCallCreateStock_then201IsReceived() throws IOException {

		final CreateStockVo createStockVo = new CreateStockVo();
		createStockVo.setCurrentPrice("INR 30.10");
		createStockVo.setName("IBM");

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
		createStockVo.setName("Sony");
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

	@Test
	public void contextLoads() {
	}

}
