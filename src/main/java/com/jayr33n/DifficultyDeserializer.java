package com.jayr33n;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jayr33n.domain.Difficulty;

import java.io.IOException;
import java.util.Arrays;

public class DifficultyDeserializer extends JsonDeserializer<Difficulty> {
    @Override
    public Difficulty deserialize(JsonParser jsonParser,
                                  DeserializationContext deserializationContext)
            throws IOException {
        var value = jsonParser.readValueAs(String.class);
        return Arrays.stream(Difficulty.values())
                .filter(difficulty -> difficulty.getName().equalsIgnoreCase(value))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
