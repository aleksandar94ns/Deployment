package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alex on 11/30/16.
 */
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue
    private long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date created;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updated;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    public long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }
}