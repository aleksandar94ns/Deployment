package com.ftn.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
public class Guest extends User {

    @ManyToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinTable(name = "guest_reservation",
            joinColumns = {@JoinColumn(name = "guest_id",  nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "reservation_id",  nullable = false)})
    private Set<Reservation> reservations;

    public Guest(){
        this.setRole(Role.GUEST);
    }

}
