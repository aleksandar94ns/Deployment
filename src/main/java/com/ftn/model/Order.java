package com.ftn.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Alex on 11/29/16.
 */
@Entity
@Table(name = "order_")
public class Order extends BaseModel {

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_tables",
            joinColumns = {@JoinColumn(name = "order_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "table_id", nullable = false)})
    private Set<RestaurantTable> restaurantTables;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Set<RestaurantTable> getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(Set<RestaurantTable> restaurantTables) {
        this.restaurantTables = restaurantTables;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "status='" + status + '\'' +
                ", reservation=" + reservation +
                ", restaurantTables=" + restaurantTables +
                ", orderItems=" + orderItems +
                '}';
    }
}
