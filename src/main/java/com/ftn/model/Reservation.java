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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reservation_table",
            joinColumns = {@JoinColumn(name = "reservation_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "restaurant_table_id", nullable = false)})
    private Set<RestaurantTable> restaurant_tables;

    public Reservation() {
    }
}
