package com.ftn.model;

import com.ftn.model.BaseModel;
import com.ftn.model.Employee;
import com.ftn.model.Shift;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Alex on 2/27/17.
 */
@Entity
public class EmployeeShift extends BaseModel {

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shift")
    private Shift shift;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee")
    private Employee employee;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "EmployeeShift{" +
                "date=" + date +
                ", shift=" + shift +
                ", employee=" + employee +
                '}';
    }
}
