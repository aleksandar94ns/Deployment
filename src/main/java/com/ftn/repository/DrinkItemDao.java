package com.ftn.repository;

import com.ftn.model.DrinkItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/23/2017.
 */
public interface DrinkItemDao extends JpaRepository<DrinkItem, Long> {

    DrinkItem findById(Long id);

    List<DrinkItem> findByDrinkCardRestaurantId(Long id);
}