package com.ftn.dto;

import com.ftn.model.Guest;
import com.ftn.model.Reservation;

import java.util.List;

/**
 * Created by Alex on 2/26/17.
 */
public class CreateReservationDTO {

    private Reservation reservation;

    private List<Guest> guests;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return "CreateReservationDTO{" +
                "reservation=" + reservation +
                ", guests=" + guests +
                '}';
    }
}
