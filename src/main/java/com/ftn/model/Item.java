package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 11/29/16.
 */
@Entity
public class Item extends BaseModel {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "price", nullable =  false)
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    public Item() {
    }
}
