package com.rohit.stockexchange.deserializer;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Local date deserializer. Is used mainly in integration tests to convert from
 * JSON string to Java LocalDateTime
 * 
 * @author Rohit Sharma
 *
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

	private static final long serialVersionUID = 1L;

	protected LocalDateTimeDeserializer() {
		super(LocalDateTime.class);
	}

	@Override
	public LocalDateTime deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
		return LocalDateTime.parse(jp.readValueAs(String.class));
	}

}
