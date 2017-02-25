package com.ftn.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class Area extends BaseModel {

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "area")
    private List<RestaurantTable> restaurantTables;

    public Area() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<RestaurantTable> getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(List<RestaurantTable> restaurantTables) {
        this.restaurantTables = restaurantTables;
    }

    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", restaurant=" + restaurant +
                ", restaurantTables=" + restaurantTables +
                '}';
    }
}
