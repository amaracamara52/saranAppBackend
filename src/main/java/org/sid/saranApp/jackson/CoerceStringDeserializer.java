package org.sid.saranApp.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Désérialise nombre ou booléen vers {@link String} (export JSON avec {@code valeurMarchandise} numérique).
 */
public class CoerceStringDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonToken t = p.currentToken();
		if (t == null || t == JsonToken.VALUE_NULL) {
			return null;
		}
		if (t == JsonToken.VALUE_STRING) {
			String s = p.getText();
			return s == null ? null : s;
		}
		if (t.isNumeric()) {
			if (p.getNumberType() == JsonParser.NumberType.INT
					|| p.getNumberType() == JsonParser.NumberType.LONG) {
				return Long.toString(p.getLongValue());
			}
			return p.getDecimalValue().toPlainString();
		}
		if (t == JsonToken.VALUE_TRUE) {
			return "true";
		}
		if (t == JsonToken.VALUE_FALSE) {
			return "false";
		}
		return p.getValueAsString();
	}
}
