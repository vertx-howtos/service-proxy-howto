package io.vertx.howtos.ebservice.beers;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Beer {

  String name;
  String style;
  int price;

  public Beer(String name, String style, int price) {
    this.name = name;
    this.style = style;
    this.price = price;
  }

  public Beer(JsonObject jsonObject) {
    BeerConverter.fromJson(jsonObject, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    BeerConverter.toJson(this, json);
    return json;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return name + " (" + style +")";
  }
}
