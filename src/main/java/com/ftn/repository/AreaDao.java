package com.ftn.repository;

import com.ftn.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 2/24/17.
 */
public interface AreaDao extends JpaRepository<Area, Long> {

    Area findByName(String name);

    Optional<Area> findById(Long id);

    List<Area> findByRestaurantId(Long id);
}
