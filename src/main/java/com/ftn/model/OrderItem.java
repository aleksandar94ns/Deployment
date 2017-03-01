package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Alex on 11/29/16.
 */
@Entity
public class OrderItem extends BaseModel {

    public enum Status {
        ORDERED,
        DELIVERED
    }

    @Column(name = "note")
    private String note;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cook_id")
    private Chef chef;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bartender_id")
    private Bartender bartender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drink_item_id")
    private DrinkItem drinkItem;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_reservation_id")
    private GuestReservation guestReservation;

    public OrderItem() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public Bartender getBartender() {
        return bartender;
    }

    public void setBartender(Bartender bartender) {
        this.bartender = bartender;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public DrinkItem getDrinkItem() {
        return drinkItem;
    }

    public void setDrinkItem(DrinkItem drinkItem) {
        this.drinkItem = drinkItem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public GuestReservation getGuestReservation() {
        return guestReservation;
    }

    public void setGuestReservation(GuestReservation guestReservation) {
        this.guestReservation = guestReservation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "note='" + note + '\'' +
                ", status=" + status +
                ", chef=" + chef +
                ", bartender=" + bartender +
                ", menuItem=" + menuItem +
                ", drinkItem=" + drinkItem +
                ", order=" + order +
                ", guestReservation=" + guestReservation +
                '}';
    }
}
