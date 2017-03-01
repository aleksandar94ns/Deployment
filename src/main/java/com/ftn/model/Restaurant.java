package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
public class Restaurant extends BaseModel {

    private String name;

    private String description;

    private String street;

    private int streetNumber;

    private String city;

    private String country;

    @ManyToOne(fetch = FetchType.EAGER)
    private RestaurantType restaurantType;

    public Restaurant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", restaurantType=" + restaurantType +
                '}';
    }
}
