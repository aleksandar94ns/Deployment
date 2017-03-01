package com.ftn.repository;

import com.ftn.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 12/16/16.
 */
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {

    Restaurant findByName(String name);

    List<Restaurant> findAllByOrderByNameAsc();

    Optional<Restaurant> findById(Long id);
}
