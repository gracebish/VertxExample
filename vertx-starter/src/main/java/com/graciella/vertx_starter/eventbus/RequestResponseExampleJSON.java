package com.graciella.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.core.Logger;

public class RequestResponseExampleJSON {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends AbstractVerticle{

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(RequestVerticle.class);
    static final String ADDRESS = "my.request.address";

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      final JsonObject message = new JsonObject()
        .put("message","Hello World.")
        .put("version", 1)
        ;
      LOG.debug("Sending message: {}", message);
      eventBus.<JsonObject>request(ADDRESS, "Hello world!", reply -> {
        LOG.debug("Response: {}", reply.result());
      });

    }
  }

  static class ResponseVerticle extends AbstractVerticle{

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(ResponseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<JsonObject>consumer(RequestVerticle.ADDRESS, message -> {
        LOG.debug("Received message: {}", message);
        message.reply(new JsonArray().add("one").add("two").add("three"));
      });
    }
  }
}
