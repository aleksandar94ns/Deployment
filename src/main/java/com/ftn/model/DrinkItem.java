package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class DrinkItem extends BaseModel {

    private String name;

    private String description;

    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drink_card_id", nullable = false)
    private DrinkCard drinkCard;

    public DrinkItem() {

    }
}
