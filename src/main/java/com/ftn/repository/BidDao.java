package com.ftn.repository;

import com.ftn.model.Bid;
import com.ftn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alek on 2/26/2017.
 */
public interface BidDao extends JpaRepository<Bid, Long> {

    Optional<Bid> findById(Long id);

    Bid findBySupplyId(Long id);

    List<Bid> findBySellerId(Long id);

    List<Bid> findBySupplyIdAndSupplyRestaurantId(Long supplyId, Long restaurantId);

    List<Bid> findBySupplyRestaurantId(Long id);
}
