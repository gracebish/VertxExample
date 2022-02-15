package com.graciella.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public class VerticleN extends AbstractVerticle {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}", getClass().getName(), " on thread {}", Thread.currentThread().getName(),
      " with config {}", config().toString());
    startPromise.complete();
  }
}
