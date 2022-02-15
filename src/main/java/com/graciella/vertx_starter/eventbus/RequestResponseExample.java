package com.graciella.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public class RequestResponseExample {

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
      final String message = "Hello World.";
      LOG.debug("Sending message: {}", message);
      eventBus.request(ADDRESS, "Hello world!", reply -> {
        LOG.debug("Response: {}", reply.result());
      });

    }
  }

  static class ResponseVerticle extends AbstractVerticle{

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(ResponseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(RequestVerticle.ADDRESS, message -> {
        LOG.debug("Received message: {}", message);
        message.reply("Received your message. Thanks.");
      });
    }
  }
}
