package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
public class Guest extends User {

    public Guest(){
        this.setRole(Role.GUEST);
    }

}
