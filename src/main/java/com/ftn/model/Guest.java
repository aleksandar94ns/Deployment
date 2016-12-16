package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
public class Guest extends User {

    @JsonBackReference("guest-reservations")
    @ManyToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinTable(name = "guest_reservation",
            joinColumns = {@JoinColumn(name = "guest_id",  nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "reservation_id",  nullable = false)})
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
