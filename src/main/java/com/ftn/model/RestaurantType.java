package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class RestaurantType extends BaseModel {

    private String name;

    private String description;

    public RestaurantType() {
    }
}
