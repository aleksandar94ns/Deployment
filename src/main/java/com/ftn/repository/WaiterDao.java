package com.ftn.repository;

import com.ftn.model.User;
import com.ftn.model.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/21/2017.
 */
public interface WaiterDao extends JpaRepository<Waiter, Long> {

    List<Waiter> findByRestaurantIdAndRole(Long id, User.Role role);
}
