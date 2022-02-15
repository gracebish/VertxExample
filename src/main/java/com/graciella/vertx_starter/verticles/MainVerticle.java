package com.graciella.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.core.Logger;

import java.util.UUID;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new com.graciella.vertx_starter.MainVerticle());
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}", getClass().getName());
    vertx.deployVerticle(new VerticleA());
    vertx.deployVerticle(new VerticleB());
    vertx.deployVerticle(VerticleN.class.getName(),
      new DeploymentOptions()
        .setInstances(4)
        .setConfig(new JsonObject()
          .put("id ", UUID.randomUUID().toString())
          .put("name", VerticleN.class.getSimpleName())

        )
    );
    startPromise.complete();
  }
}
