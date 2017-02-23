package com.ftn.repository;

import com.ftn.model.Chef;
import com.ftn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/23/2017.
 */
public interface ChefDao extends JpaRepository<Chef, Long> {

    Chef findById(Long id);

    List<Chef> findByRestaurantIdAndRole(Long id, User.Role role);
}