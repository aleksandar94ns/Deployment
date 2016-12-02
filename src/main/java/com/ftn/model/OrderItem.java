package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.Nullable;

import javax.persistence.*;

/**
 * Created by Alex on 11/29/16.
 */
@Entity
public class OrderItem extends BaseModel {

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Version
    private int version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cook_id")
    private Chef chef;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bartender_id")
    private Bartender bartender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Reservation reservation;

    public OrderItem() {
    }
}
