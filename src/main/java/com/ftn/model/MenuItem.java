package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class MenuItem extends BaseModel {

    private double price;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    public MenuItem() {
    }
}
