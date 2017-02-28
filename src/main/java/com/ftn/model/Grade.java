package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/16.
 */
@Entity
public class Grade extends BaseModel {

    private int meal;

    private int waiter;

    private int restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    public Grade() {
    }

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }

    public int getWaiter() {
        return waiter;
    }

    public void setWaiter(int waiter) {
        this.waiter = waiter;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "meal=" + meal +
                ", waiter=" + waiter +
                ", restaurant=" + restaurant +
                ", guest=" + guest +
                ", reservation=" + reservation +
                '}';
    }
}
