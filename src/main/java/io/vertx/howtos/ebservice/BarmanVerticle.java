package io.vertx.howtos.ebservice;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.howtos.ebservice.beers.BarmanService;
import io.vertx.howtos.ebservice.beers.impl.BarmanServiceImpl;
import io.vertx.serviceproxy.ServiceBinder;

public class BarmanVerticle extends VerticleBase {

  @Override
  public Future<?> start() {
    BarmanService service = new BarmanServiceImpl(WebClient.create(vertx)); // <1>

    MessageConsumer<JsonObject> consumer = new ServiceBinder(vertx) // <2>
      .setAddress("beers.services.myapplication") // <3>
      .register(BarmanService.class, service);// <4>

    return consumer.completion();
  }
}
