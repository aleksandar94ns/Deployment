package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 2/26/17.
 */
@Entity
public class GuestReservation extends BaseModel {

    public enum Status {
        PENDING,
        ACCEPTED,
        DECLINED
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest", nullable = false)
    private Guest guest;

    public GuestReservation() {
    }

    public GuestReservation(Reservation reservation, Guest guest) {
        this.reservation = reservation;
        this.guest = guest;
        this.status = Status.PENDING;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    @Override
    public String toString() {
        return "GuestReservation{" +
                "status=" + status +
                ", reservation=" + reservation +
                ", guest=" + guest +
                '}';
    }
}
