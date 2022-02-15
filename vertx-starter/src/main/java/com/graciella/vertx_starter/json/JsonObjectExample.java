package com.graciella.vertx_starter.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonObjectExample {

  @Test
  void jsonObjectCanBeMapped(){
    final JsonObject jsonObject = new JsonObject();
    jsonObject.put("id", 1);
    jsonObject.put("name", "Joan");
    jsonObject.put("Loves_vertx", true);

    final String encoded = jsonObject.encode();
    assertEquals("", encoded);

    final JsonObject decodedJsonObject = new JsonObject(encoded);
    assertEquals(jsonObject, decodedJsonObject);
  }

  void jsonObjectCanBeCreatedFromMap() {
    final Map<String, Object> myMap = new HashMap<>();
    myMap.put("id", 1);
    myMap.put("name", "Joan");
    myMap.put("Loves_vertx", true);
    final JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap, asJsonObject.getMap());
  }

  @Test
  void jsonArrayCanBeMapped(){
    final JsonArray myJsonArray = new JsonArray();
    myJsonArray
      .add(new JsonObject().put("Id", 1))
      .add(new JsonObject().put("Id", 2))
      .add(new JsonObject().put("Id", 3))
    ;
    assertEquals("[{\"Id\":1},{\"Id\":2},{\"Id\":3}]", myJsonArray.encode());
  }

  @Test
  void canMapJavaObjects(){
    final Person person = new Person(1, "Joan", true);
    final JsonObject Joan = JsonObject.mapFrom(person);
    assertEquals(person.getId(), Joan.getInteger("Id"));
    assertEquals(person.getName(), Joan.getString("name"));
    assertEquals(person.isLoves_vertx(), Joan.getBoolean("true"));
  }
}

