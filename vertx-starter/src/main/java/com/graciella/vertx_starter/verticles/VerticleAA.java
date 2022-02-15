package com.graciella.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public class VerticleAA extends AbstractVerticle {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}", getClass().getName());
    startPromise.complete();
  }

  @Override
  public void stop(final Promise<Void> stopPromise) throws Exception {
    LOG.debug("Stop {}", getClass().getName());
    stopPromise.complete();
  }
}
