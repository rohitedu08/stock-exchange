package com.rohit.stockexchange.resources.viewobjects;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DateDistruction {

	public static void main(String[] args) throws IOException {


		String json = "{"+"\"name\""+":"+"\"party\""+","+"\"lastUpdate\""+":"+"\"2018-10-15T13:52:53\""+"}";
		 System.out.println(json);
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		    ObjectMapper mapper = new ObjectMapper();
		    mapper.setDateFormat(df);
		 
		    StockVo vo = mapper.readerFor(StockVo.class).readValue(json);

	}

}
