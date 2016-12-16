package com.ftn.repository;

import com.ftn.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alex on 12/16/16.
 */
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByOrderByNameAsc();
}
