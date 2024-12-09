package io.vertx.howtos.ebservice;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.howtos.ebservice.beers.BarmanService;

public class DrunkVerticle extends VerticleBase {

  @Override
  public Future<?> start() {
    BarmanService barmanService = BarmanService.createProxy(vertx, "beers.services.myapplication"); // <1>
    return barmanService.giveMeARandomBeer("homer") // <2>
      .onSuccess(beer1 -> System.out.println("My first beer is a " + beer1.getName() + " and it costs " + beer1.getPrice() + "€"))  // <3>
      .compose(v -> vertx.timer(1500))
      .compose(v -> barmanService.giveMeARandomBeer("homer")) // <4>
      .onSuccess(beer2 -> System.out.println("My second beer is a " + beer2.getName() + " and it costs " + beer2.getPrice() + "€")) // <5>
      .compose(v -> barmanService.getMyBill("homer")) // <6>
      .onSuccess(bill -> {
        System.out.println("My bill with the bar is " + bill + "€");
        barmanService.payMyBill("homer"); // <7>
      });
  }
}
