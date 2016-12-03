package com.ftn.repository;

import com.ftn.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alex on 12/3/16.
 */
public interface FriendshipDao extends JpaRepository<Friendship, Long> {

    List<Friendship> findFriendshipByStatusAndOriginatorIdOrRecipientId(Friendship.FriendshipStatus friendshipStatus, long originatorId, long recipientId);

    List<Friendship> findByOriginatorIdOrRecipientId(long originatorId, long recipientId);
}
