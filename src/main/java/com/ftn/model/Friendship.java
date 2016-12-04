package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 12/3/16.
 */
@Entity
public class Friendship extends BaseModel {

    public enum FriendshipStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "originator_id", nullable = false)
    private Guest originator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Guest recipient;

    public Friendship() {
    }

    public Friendship(Guest originator, Guest recipient) {
        this.status = FriendshipStatus.PENDING;
        this.originator = originator;
        this.recipient = recipient;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public Guest getOriginator() {
        return originator;
    }

    public void setOriginator(Guest originator) {
        this.originator = originator;
    }

    public Guest getRecipient() {
        return recipient;
    }

    public void setRecipient(Guest recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "status=" + status +
                ", originator=" + originator +
                ", recipient=" + recipient +
                '}';
    }
}
