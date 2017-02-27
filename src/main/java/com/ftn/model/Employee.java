package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 2/25/17.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User {

}
