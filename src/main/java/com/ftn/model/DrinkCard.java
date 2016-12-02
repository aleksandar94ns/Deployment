package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class DrinkCard extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id",nullable = false)
    private Restaurant restaurant;

    public DrinkCard(){

    }

}
