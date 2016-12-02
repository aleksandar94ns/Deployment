package com.ftn.model;

import javax.persistence.Entity;

/**
 * Created by Alex on 10/28/16.
 */
@Entity
public class Seller extends User {

    public Seller() {
        this.setRole(Role.SELLER);
    }
}
