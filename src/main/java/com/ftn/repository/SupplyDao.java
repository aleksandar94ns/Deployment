package com.ftn.repository;

import com.ftn.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 2/25/17.
 */
public interface SupplyDao extends JpaRepository<Supply, Long> {

    Supply findById(Long id);

    List<Supply> findByExpirationAfter(Date expiration);

    List<Supply> findByRestaurantIdAndExpirationBefore(Long id, Date expiration);
}