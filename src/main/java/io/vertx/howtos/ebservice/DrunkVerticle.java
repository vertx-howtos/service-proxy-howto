package io.vertx.howtos.ebservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.howtos.ebservice.beers.BarmanService;

public class DrunkVerticle extends AbstractVerticle {
  @Override
  public void start(Future<Void> startFuture) {
    BarmanService barmanService = BarmanService.createProxy(vertx, "beers.services.myapplication"); // <1>

    barmanService.giveMeARandomBeer("homer", b1 -> { // <2>
      if (b1.failed()) { // <3>
        System.err.println("Cannot get my first beer!");
        startFuture.fail(b1.cause());
        return;
      }
      System.out.println("My first beer is a " + b1.result() + " and it costs " + b1.result().getPrice() + "€"); // <4>
      vertx.setTimer(1500, l ->
        barmanService.giveMeARandomBeer("homer", b2 -> { // <5>
          if (b2.failed()) {
            System.out.println("Cannot get my second beer!");
            startFuture.fail(b2.cause());
            return;
          }
          System.out.println("My second beer is a " + b2.result() + " and it costs " + b2.result().getPrice() + "€"); // <6>
          barmanService.getMyBill("homer", billAr -> {
            System.out.println("My bill with the bar is " + billAr.result()); // <7>
            barmanService.payMyBill("homer"); // <8>
            startFuture.complete();
          });
        })
      );
    });

  }
}
