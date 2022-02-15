package com.graciella.vertx_starter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample<var> {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(FuturePromiseExample.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id->{
      promise.complete("Success");
      LOG.debug("Success");
      context.completeNow();
    });
    LOG.debug("End");
  }

  @Test
  void promise_failure(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id->{
      promise.fail(new RuntimeException("Failed"));
      LOG.debug("Failed");
      context.completeNow();
    });
    LOG.debug("End");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id->{
      promise.complete("Success");
      LOG.debug("Timer done.");

    });
    final Future<String> future = promise.future();
    future
      .onSuccess(result ->{
        LOG.debug("Result: {}", result);
        context.completeNow();
      })
    .onFailure(context::failNow)
    ;
  }

  @Test
  void future_failure(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id->{
      promise.fail(new RuntimeException("Failed"));
      LOG.debug("Timer done.");
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(result ->{
        LOG.debug("Result: {}", result);
        context.completeNow();
      })
      .onFailure(error -> {
        LOG.debug("Result: ", error);
        context.completeNow();
      })
    ;
  }

  @Test
  void future_map(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id->{
      promise.complete("Success");
      LOG.debug("Timer done.");
    });
    final Future<String> future = promise.future();
    future
      .map(asString -> {
        LOG.debug("Map string to JsonObject");
        return new JsonObject().put("key", asString);
      })//Future<JsonObject>
      .map(jsonObject -> new JsonArray().add(jsonObject))// Future<JsonObject>
      .onSuccess(result ->{
        LOG.debug("Result: {} of type {}", result, result.getClass().getSimpleName());
        context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext context){
    vertx.createHttpServer()
    .requestHandler(request -> LOG.debug("{}", request))
    .listen(10_000)
    .compose(Server ->{
      LOG.info("Another task");
      return Future.succeededFuture(Server);
    })
     .compose(Server -> {
       LOG.info("Even more");
       return Future.succeededFuture(Server);
     })
    .onFailure(context::failNow)
    .onSuccess(Server ->{
      LOG.debug("Server started on port {}", Server.actualPort());
      context.completeNow();
    });
  }

  @Test
  void future_composition(Vertx vertx, VertxTestContext context){
    /*var one = (var) Promise.<Void>promise();
    var two = (var) Promise.<Void>promise();
    var three = (var) Promise.<Void>promise();

    var futureOne = one.future();
    var futureTwo = two.future();
    var futureThree = three.future();

    CompositeFuture.all(futureOne,futureTwo, futureThree)
      .onFailure(context::failNow)
      .onSuccess(result ->{
        LOG.debug("Success");
        context.completeNow();
      });
    vertx.setTimer(500, id ->{
      one.complete();
      two.complete();
      three.fail("three failed");
    });**/
  }
}


