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

    @Override
    public String toString() {
        return "RestaurantType{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
