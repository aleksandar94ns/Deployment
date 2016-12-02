package com.ftn.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alex on 11/29/16.
 */
@Entity
public class Bid extends BaseModel {

    @Column(name = "price")
    private double price;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller", nullable = false)
    private Seller seller;

    @Column(name = "currency")
    private String currency;

    public Bid() {
    }
}
