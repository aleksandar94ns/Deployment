package com.ftn.repository;

import com.ftn.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 3/1/2017.
 */
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByGuestReservationReservationRestaurantId(Long id);
}
