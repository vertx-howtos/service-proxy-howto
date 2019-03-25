package io.vertx.howtos.ebservice;

import io.vertx.core.Vertx;

public class BeersApplication {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new BarmanVerticle(), ar -> {
      System.out.println("The barman is ready to serve you");
      vertx.deployVerticle(new DrunkVerticle(), ar2 -> {
        vertx.close();
      });
    });
  }

}
