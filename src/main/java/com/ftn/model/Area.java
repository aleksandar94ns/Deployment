package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class Area extends BaseModel {

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    public Area() {
    }
}
