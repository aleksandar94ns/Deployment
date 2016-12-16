package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
public class Restaurant extends BaseModel {

    private String name;

    private String description;

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

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", restaurantType=" + restaurantType +
                '}';
    }
}
