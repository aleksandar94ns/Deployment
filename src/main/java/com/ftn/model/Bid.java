package com.ftn.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alex on 11/29/16.
 */
@Entity
public class Bid extends BaseModel {

    @Version
    private int version;

    @Column(name = "price")
    private double price;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller", nullable = false)
    private Seller seller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supply", nullable = false)
    private Supply supply;

    @Column(name = "currency")
    private String currency;

    public Bid() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "price=" + price +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", seller=" + seller +
                ", supply=" + supply +
                ", currency='" + currency + '\'' +
                '}';
    }
}
