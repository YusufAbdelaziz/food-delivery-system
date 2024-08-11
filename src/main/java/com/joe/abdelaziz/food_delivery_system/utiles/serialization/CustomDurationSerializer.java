package com.joe.abdelaziz.food_delivery_system.utiles.serialization;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDurationSerializer extends JsonSerializer<Duration> {
  @Override
  public void serialize(Duration duration, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();
    gen.writeNumberField("seconds", duration.getSeconds());
    gen.writeStringField("display", formatDuration(duration));
    gen.writeEndObject();
  }

  private String formatDuration(Duration duration) {
    long hours = duration.toHours();
    long minutes = duration.toMinutesPart();
    long seconds = duration.toSecondsPart();

    StringBuilder sb = new StringBuilder();
    if (hours > 0) {
      sb.append(hours).append(hours == 1 ? " hour" : " hours");
    }
    if (minutes > 0) {
      if (sb.length() > 0)
        sb.append(" ");
      sb.append(minutes).append(minutes == 1 ? " minute" : " minutes");
    }
    if (seconds > 0 || sb.length() == 0) {
      if (sb.length() > 0)
        sb.append(" ");
      sb.append(seconds).append(seconds == 1 ? " second" : " seconds");
    }
    return sb.toString();
  }
}
