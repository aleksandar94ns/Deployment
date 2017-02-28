package com.ftn.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Alex on 2/25/17.
 */
@Entity
public class Shift extends BaseModel {

    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private Date day;

    @DateTimeFormat(pattern="HH:mm:ss")
    @Column(nullable = false)
    private Date startHour;

    @DateTimeFormat(pattern="HH:mm:ss")
    @Column(nullable = false)
    private Date endHour;

    public Shift() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "restaurant=" + restaurant +
                ", day=" + day +
                ", startHour='" + startHour + '\'' +
                ", endHour='" + endHour + '\'' +
                '}';
    }
}
