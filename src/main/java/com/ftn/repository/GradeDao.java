package com.ftn.repository;

import com.ftn.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/27/2017.
 */

public interface GradeDao extends JpaRepository<Grade, Long> {

    List<Grade> findByReservationRestaurantId(Long id);
}
