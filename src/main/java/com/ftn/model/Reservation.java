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

    private Date arrivalDate;

    private Date departureDate;

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

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
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
                "arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", guests=" + guests +
                ", restaurant_tables=" + restaurant_tables +
                ", restaurant=" + restaurant +
                '}';
    }
}
