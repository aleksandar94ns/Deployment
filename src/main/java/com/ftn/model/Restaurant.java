package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
public class Restaurant extends BaseModel {

    private String name;

    private String descriptions;

    @ManyToOne(fetch = FetchType.EAGER)
    private RestaurantType restaurantType;

    public Restaurant() {
    }
}
