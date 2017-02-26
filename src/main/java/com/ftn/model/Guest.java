package com.ftn.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
public class Guest extends User {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "guests")
    private Set<Reservation> reservations;

    public Guest(){
        this.setRole(Role.GUEST);
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "reservations=" + reservations +
                '}';
    }
}
