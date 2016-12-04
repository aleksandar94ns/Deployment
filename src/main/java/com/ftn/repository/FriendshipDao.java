package com.ftn.repository;

import com.ftn.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alex on 12/3/16.
 */
public interface FriendshipDao extends JpaRepository<Friendship, Long> {

    Friendship findById(long id);

    List<Friendship> findByOriginatorIdOrRecipientId(long originatorId, long recipientId);
}
