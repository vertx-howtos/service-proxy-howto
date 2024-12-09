package io.vertx.howtos.ebservice.beers;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

@VertxGen
@ProxyGen // <1>
public interface BarmanService {

  Future<Beer> giveMeARandomBeer(String customerName); // <2>

  Future<Integer> getMyBill(String customerName); // <3>

  void payMyBill(String customerName); // <4>

  static BarmanService createProxy(Vertx vertx, String address) { // <5>
    return new BarmanServiceVertxEBProxy(vertx, address);
  }

}
