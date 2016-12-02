package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Alex on 11/29/16.
 */
@Entity
@Table(name = "order_pls")
public class Order extends BaseModel {

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_tables",
            joinColumns = {@JoinColumn(name = "order_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "table_id", nullable = false)})
    private Set<RestaurantTable> restaurantTables;

    public Order() {
    }
}
