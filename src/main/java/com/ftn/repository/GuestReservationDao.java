package com.ftn.repository;

import com.ftn.model.GuestReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 2/26/17.
 */
public interface GuestReservationDao extends JpaRepository<GuestReservation, Long> {

    List<GuestReservation> findByReservationId(Long id);

    List<GuestReservation> findByGuestId(Long id);

    List<GuestReservation> findByGuestIdAndReservationArrivalDateBefore(Long id, Date arrivalDate);

    Optional<GuestReservation> findByGuestIdAndReservationId(Long guestId, Long reservationId);
}
