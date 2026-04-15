package org.sid.saranApp.jackson;

import java.io.IOException;

import org.sid.saranApp.enume.TypeUniteEnum;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Accepte les exports JSON où une unité est une chaîne vide {@code ""} (non {@code null}) — traité comme absence de valeur.
 */
public class TypeUniteEnumDeserializer extends JsonDeserializer<TypeUniteEnum> {

    @Override
    public TypeUniteEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String s = p.getValueAsString();
        if (s == null || s.isBlank()) {
            return null;
        }
        return TypeUniteEnum.valueOf(s.trim());
    }
}
