package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Alex on 10/28/2016.
 */
@Entity
public class Bartender extends User {

    private int dressSize;

    private int footwearSize;

    private Date birthDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    public Bartender(){
        this.setRole(Role.BARTENDER);
    }

}
