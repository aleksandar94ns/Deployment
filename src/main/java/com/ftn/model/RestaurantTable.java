package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JsonBackReference
    private Area area;

    public RestaurantTable() {
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "horizontalPosition=" + horizontalPosition +
                ", verticalPosition=" + verticalPosition +
                ", area=" + area +
                '}';
    }
}
