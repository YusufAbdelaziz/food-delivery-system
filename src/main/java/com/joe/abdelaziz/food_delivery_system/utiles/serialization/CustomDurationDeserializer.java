package com.joe.abdelaziz.food_delivery_system.utiles.serialization;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CustomDurationDeserializer extends JsonDeserializer<Duration> {
  @Override
  public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);
    if (node.has("seconds")) {
      long seconds = node.get("seconds").asLong();
      return Duration.ofSeconds(seconds);
    } else if (node.has("display")) {
      return parseDurationString(node.get("display").asText());
    }
    throw new JsonParseException(p, "Unable to deserialize Duration");
  }

  private Duration parseDurationString(String durationStr) {

    String[] parts = durationStr.split("\\s+");
    Duration duration = Duration.ZERO;
    for (int i = 0; i < parts.length; i += 2) {
      long value = Long.parseLong(parts[i]);
      String unit = parts[i + 1].toLowerCase();
      switch (unit) {
        case "hour":
        case "hours":
          duration = duration.plusHours(value);
          break;
        case "minute":
        case "minutes":
          duration = duration.plusMinutes(value);
          break;
        case "second":
        case "seconds":
          duration = duration.plusSeconds(value);
          break;
        default:
          throw new IllegalArgumentException("Unknown time unit: " + unit);
      }
    }
    return duration;
  }
}