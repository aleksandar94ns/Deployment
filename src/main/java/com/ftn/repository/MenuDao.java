package com.ftn.repository;

import com.ftn.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/23/2017.
 */
public interface MenuDao extends JpaRepository<Menu, Long> {

    List<Menu> findByRestaurantId(Long id);
}