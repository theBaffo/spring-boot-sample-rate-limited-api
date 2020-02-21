package com.example.sampleratelimitedapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 *
 *
 * <h1>Hotel</h1>
 *
 * <p>A simple POJO describing an Hotel entity.
 *
 * <p>This class is used to persist and retrieve Hotel information from the Database using JPA.
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@Entity
@Table(name = "hotels")
public class Hotel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String city;

  @NotBlank private String room;

  @NotBlank private Integer price;

  public Hotel() {}

  public Hotel(Long id, String city, String room, Integer price) {
    this.id = id;
    this.city = city;
    this.room = room;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }
}
