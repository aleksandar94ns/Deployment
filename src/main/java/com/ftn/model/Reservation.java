package com.ftn.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Alex on 10/28/16.
 */
@Entity
public class Reservation extends BaseModel {

    private Date date;

    private int start_hour;

    private int end_hour;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reservations")
    private List<Guest> guests;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reservation_table",
            joinColumns = {@JoinColumn(name = "reservation_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "restaurant_table_id", nullable = false)})
    private Set<RestaurantTable> restaurant_tables;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Reservation() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public Set<RestaurantTable> getRestaurant_tables() {
        return restaurant_tables;
    }

    public void setRestaurant_tables(Set<RestaurantTable> restaurant_tables) {
        this.restaurant_tables = restaurant_tables;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "date=" + date +
                ", start_hour=" + start_hour +
                ", end_hour=" + end_hour +
                ", guests=" + guests +
                ", restaurant_tables=" + restaurant_tables +
                ", restaurant=" + restaurant +
                '}';
    }
}
