package com.ftn.repository;

import com.ftn.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 2/25/17.
 */
public interface ReservationDao extends JpaRepository<Reservation, Long> {

    List<Reservation> findByArrivalDateLessThanEqualAndDepartureDateGreaterThanEqual(Date departureDate, Date arrivalDate);
}