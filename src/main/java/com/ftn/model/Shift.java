package com.ftn.model;

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

    @ManyToOne
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private Date day;

    @Column(nullable = false)
    private boolean valid;

    @Column(nullable = false)
    private String startHour;

    @Column(nullable = false)
    private String endHour;

    public Shift() {
    }
}
