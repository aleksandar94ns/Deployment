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

    @Transient
    private boolean reserved;

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

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "horizontalPosition=" + horizontalPosition +
                ", verticalPosition=" + verticalPosition +
                ", area=" + area +
                ", reserved=" + reserved +
                '}';
    }
}
