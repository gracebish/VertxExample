package com.graciella.vertx_starter.eventbuscustomcodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public class PingPongExample {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), logOnError());
    vertx.deployVerticle(new PongVerticle(), logOnError());
      }

  private static Handler<AsyncResult<String>> logOnError() {
    return ar -> {
      if (ar.failed()) {
        LOG.error("err", ar.cause());
      }
    };
  }

  static class PingVerticle extends AbstractVerticle{

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(PingVerticle.class);
    static final String ADDRESS = PingVerticle.class.getName();

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      final Ping message = new Ping("Hello", true);
      LOG.debug("Sending message: {}", message);
      //Register default codec
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      eventBus.<Pong>request(ADDRESS, "Hello world!", reply -> {
        if(reply.failed()){
          LOG.error("Failed ", reply.cause());
          return;
        }
        LOG.debug("Response: {}", reply.result());
      });
      startPromise.complete();
    }
  }

  static class PongVerticle extends AbstractVerticle{

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(PongVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS, message -> {
        LOG.debug("Received message: {}", message);
        eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
        message.reply(new Pong(0));
      }).exceptionHandler(error ->{
        LOG.error("Error: ", error);
      });
      startPromise.complete();
    }
  }
}
