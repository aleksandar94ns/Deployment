package com.ftn.repository;

import com.ftn.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/27/2017.
 */
public interface ShiftDao extends JpaRepository<Shift, Long> {

    Shift findByName(String name);

    List<Shift> findByRestaurantId(Long id);
}
