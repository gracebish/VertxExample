package com.graciella.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

import java.time.Duration;

public class PublishSubscribeExample {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(Subscriber2.class.getName(), new DeploymentOptions().setInstances(2));
  }

  public static class Publish extends AbstractVerticle{

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(Duration.ofMillis(10).toMillis(), id->{
        vertx.eventBus().publish(Publish.class.getName(), "A message for everyone");
      });
    }
  }

  public static class Subscriber1 extends AbstractVerticle{

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(Subscriber1.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
        LOG.debug("Received " + message);
      });
    }
  }

  public static class Subscriber2 extends AbstractVerticle{

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(Subscriber2.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
        LOG.debug("Received " + message);
      });
    }
  }
}
