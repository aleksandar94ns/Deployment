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
}
