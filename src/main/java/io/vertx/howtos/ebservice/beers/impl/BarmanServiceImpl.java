package io.vertx.howtos.ebservice.beers.impl;

import io.vertx.core.Future;
import io.vertx.core.http.HttpResponseExpectation;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.howtos.ebservice.beers.BarmanService;
import io.vertx.howtos.ebservice.beers.Beer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BarmanServiceImpl implements BarmanService {

  Map<String, Integer> bills;
  Random random;
  WebClient webClient;

  public BarmanServiceImpl(WebClient webClient) {
    this.bills = new HashMap<>();
    this.random = new Random();
    this.webClient = webClient;
  }

  // tag::giveMeARandomBeer[]
  @Override
  public Future<Beer> giveMeARandomBeer(String customerName) {
    return webClient
      .get(443, "www.craftbeernamegenerator.com", "/api/api.php?type=classic") // <1>
      .ssl(true)
      .send()
      .expecting(HttpResponseExpectation.SC_OK) // <2>
      .map(bufferHttpResponse -> {
        JsonObject result = bufferHttpResponse.bodyAsJsonObject();
        Beer beer = new Beer( // <3>
          result.getJsonObject("data").getString("name"),
          result.getJsonObject("data").getString("style"),
          3 + random.nextInt(5)
        );
        System.out.println("Generated a new Beer! " + beer);
        bills.merge(customerName, beer.getPrice(), Integer::sum); // <4>
        return beer;
      });
  }
  // end::giveMeARandomBeer[]

  // tag::getMyBill[]
  @Override
  public Future<Integer> getMyBill(String customerName) {
    return Future.succeededFuture(bills.get(customerName));
  }
  // end::getMyBill[]

  // tag::payMyBill[]
  @Override
  public void payMyBill(String customerName) {
    bills.remove(customerName);
    System.out.println("Removed debt of " + customerName);
  }
  // end::payMyBill[]
}
