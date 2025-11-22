package com.damvih.dto.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class GeocodingApiResponseDeserializer extends JsonDeserializer<GeocodedWeatherDto> {

    @Override
    public GeocodedWeatherDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode root = oc.readTree(jsonParser);

        JsonNode weather = root.get("weather").get(0);
        JsonNode main = root.get("main");

        return new GeocodedWeatherDto(
                root.get("sys").get("country").textValue(),
                weather.get("main").textValue(),
                weather.get("icon").textValue(),
                main.get("temp").asInt(),
                main.get("feels_like").asInt(),
                main.get("pressure").asInt(),
                main.get("humidity").asInt(),
                root.get("wind").get("speed").asDouble()
        );
    }

}
