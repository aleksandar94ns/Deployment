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

    @Column(nullable = false)
    private boolean active = true;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModel baseModel = (BaseModel) o;

        return id == baseModel.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}