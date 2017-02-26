package com.ftn.repository;

import com.ftn.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Alex on 2/26/17.
 */
public interface OrderDao extends JpaRepository<Order, Long> {
}
