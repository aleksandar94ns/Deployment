package com.ftn.repository;

import com.ftn.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/23/2017.
 */
public interface MenuItemDao extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByMenuRestaurantId(Long id);
}
