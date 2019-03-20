package io.vertx.howtos.ebservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClient;
import io.vertx.howtos.ebservice.beers.BarmanService;
import io.vertx.howtos.ebservice.beers.impl.BarmanServiceImpl;
import io.vertx.serviceproxy.ServiceBinder;

public class BarmanVerticle extends AbstractVerticle {
  @Override
  public void start() {
    BarmanService service = new BarmanServiceImpl(WebClient.create(vertx)); // <1>

    new ServiceBinder(vertx) // <2>
      .setAddress("beers.services.myapplication") // <3>
      .register(BarmanService.class, service); // <4>
  }
}
