package com.ftn.repository;

import com.ftn.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/26/2017.
 */
public interface RestaurantTableDao extends JpaRepository<RestaurantTable, Long> {

    RestaurantTable findById(Long id);

    List<RestaurantTable> findByAreaRestaurantId(Long id);

    List<RestaurantTable> findByAreaId(Long areaId);
}
