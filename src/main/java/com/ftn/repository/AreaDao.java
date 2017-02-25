package com.ftn.repository;

import com.ftn.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Alex on 2/24/17.
 */
public interface AreaDao extends JpaRepository<Area, Long> {
}
