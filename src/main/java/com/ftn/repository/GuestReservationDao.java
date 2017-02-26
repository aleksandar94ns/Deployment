package com.ftn.repository;

import com.ftn.model.GuestReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 2/26/17.
 */
public interface GuestReservationDao extends JpaRepository<GuestReservation, Long> {

    List<GuestReservation> findByGuestId(Long id);

    Optional<GuestReservation> findByGuestIdAndReservationId(Long guestId, Long reservationId);
}
