package com.ftn.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Alex on 10/28/16.
 */
@Entity
public class Reservation extends BaseModel {

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S")
    private Date arrivalDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S")
    private Date departureDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reservations")
    private List<Guest> guests;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reservation_table",
            joinColumns = {@JoinColumn(name = "reservation_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "restaurant_table_id", nullable = false)})
    private Set<RestaurantTable> restaurantTables;

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

    public Set<RestaurantTable> getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(Set<RestaurantTable> restaurantTables) {
        this.restaurantTables = restaurantTables;
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
                ", restaurantTables=" + restaurantTables +
                ", restaurant=" + restaurant +
                '}';
    }
}
