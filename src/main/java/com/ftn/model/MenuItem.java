package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class MenuItem extends BaseModel {

    private String name;

    private double price;

    private String description;

    @JoinColumn(name = "speciality_id", nullable = false)
    private String speciality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    public MenuItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", speciality='" + speciality + '\'' +
                ", menu=" + menu +
                '}';
    }
}
