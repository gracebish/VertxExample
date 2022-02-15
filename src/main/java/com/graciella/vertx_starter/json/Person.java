package com.graciella.vertx_starter.json;

public class Person {

  private Integer id;
  private String name;
  private boolean loves_vertx;

  public Person(){

  }

  public Person(Integer id, String name, boolean loves_vertx) {
    this.id = id;
    this.name = name;
    this.loves_vertx = loves_vertx;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isLoves_vertx() {
    return loves_vertx;
  }

  public void setLoves_vertx(boolean loves_vertx) {
    this.loves_vertx = loves_vertx;
  }
}
