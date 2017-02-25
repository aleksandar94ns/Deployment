package com.ftn.repository;

import com.ftn.model.Bartender;
import com.ftn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alek on 2/21/2017.
 */
public interface BartenderDao extends JpaRepository<Bartender, Long> {

    List<Bartender> findByRestaurantIdAndRole(Long id, User.Role role);
}
