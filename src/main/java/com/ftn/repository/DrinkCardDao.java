package com.ftn.repository;

import com.ftn.model.DrinkCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/23/2017.
 */
public interface DrinkCardDao extends JpaRepository<DrinkCard, Long> {

    DrinkCard findById(Long id);

    List<DrinkCard> findByRestaurantId(Long id);
}