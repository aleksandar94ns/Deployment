package com.ftn.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class Menu extends BaseModel {

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    private List<MenuItem> menuItems;

    public Menu() {
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

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", restaurant=" + restaurant +
                ", menuItems=" + menuItems +
                '}';
    }
}
