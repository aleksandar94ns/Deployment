package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/28/16.
 */
@Entity
public class RestaurantTable extends BaseModel {

    private int horizontalPosition;

    private int verticalPosition;

    @ManyToOne
    @JoinColumn(name = "area", nullable = false)
    private Area area;

    public RestaurantTable() {
    }
}
