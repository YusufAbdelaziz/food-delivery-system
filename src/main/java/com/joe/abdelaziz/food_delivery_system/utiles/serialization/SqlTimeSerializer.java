package com.joe.abdelaziz.food_delivery_system.utiles.serialization;

import java.io.IOException;
import java.sql.Time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class SqlTimeSerializer extends JsonSerializer<Time> {
  @Override
  public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeString(time.toString());
  }
}
