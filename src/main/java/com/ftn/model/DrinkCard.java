package com.ftn.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class DrinkCard extends BaseModel {

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id",nullable = false)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "drinkCard")
    private List<DrinkItem> drinkItems;

    public DrinkCard(){
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

    public List<DrinkItem> getDrinkItems() {
        return drinkItems;
    }

    public void setDrinkItems(List<DrinkItem> drinkItems) {
        this.drinkItems = drinkItems;
    }

    @Override
    public String toString() {
        return "DrinkCard{" +
                "name='" + name + '\'' +
                ", restaurant=" + restaurant +
                ", drinkItems=" + drinkItems +
                '}';
    }
}
