package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class DrinkItem extends BaseModel {

    private String name;

    private String description;

    private double price;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drink_card_id", nullable = false)
    private DrinkCard drinkCard;

    public DrinkItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DrinkCard getDrinkCard() {
        return drinkCard;
    }

    public void setDrinkCard(DrinkCard drinkCard) {
        this.drinkCard = drinkCard;
    }

    @Override
    public String toString() {
        return "DrinkItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", drinkCard=" + drinkCard +
                '}';
    }
}
