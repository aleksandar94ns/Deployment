package com.ftn.model;

import com.ftn.protocol.HasUniform;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class Waiter extends User implements HasUniform {

    private int dressSize;

    private int footwearSize;

    private Date birthDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    public Waiter() {
        this.setRole(Role.WAITER);
    }

    public int getDressSize() {
        return dressSize;
    }

    public void setDressSize(int dressSize) {
        this.dressSize = dressSize;
    }

    public int getFootwearSize() {
        return footwearSize;
    }

    public void setFootwearSize(int footwearSize) {
        this.footwearSize = footwearSize;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Waiter{" +
                "dressSize=" + dressSize +
                ", footwearSize=" + footwearSize +
                ", birthDate=" + birthDate +
                ", restaurant=" + restaurant +
                '}';
    }
}
