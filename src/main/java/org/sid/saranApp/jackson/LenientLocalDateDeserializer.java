package org.sid.saranApp.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Accepte null, chaîne vide, ou une date ISO ; sinon null pour éviter les erreurs sur export JSON.
 */
public class LenientLocalDateDeserializer extends JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonToken t = p.currentToken();
		if (t == null || t == JsonToken.VALUE_NULL) {
			return null;
		}
		if (t == JsonToken.VALUE_STRING) {
			String s = p.getText();
			if (s == null || s.isBlank()) {
				return null;
			}
			try {
				return LocalDate.parse(s.trim());
			} catch (DateTimeParseException e) {
				return null;
			}
		}
		return (LocalDate) ctxt.handleUnexpectedToken(LocalDate.class, p);
	}
}
