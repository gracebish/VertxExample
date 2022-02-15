package com.graciella.vertx_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public class WorkerVerticle extends AbstractVerticle {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(WorkerVerticle.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Deployed as worker verticle.");
    startPromise.complete();
    Thread.sleep(5000);
  }
}
