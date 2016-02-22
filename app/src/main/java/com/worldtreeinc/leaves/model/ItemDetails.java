package com.worldtreeinc.leaves.model;

import com.parse.ParseObject;

/**
 * Created by andela on 22/02/2016.
 */
public class ItemDetails extends ParseObject {

    private Double numberOfBids = 0.0;
    private Double maximumBid = 0.0;
    private Double minimumBid = 0.0;

    public ItemDetails() {
    }

    public Double getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(Double numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public Double getMaximumBid() {
        return maximumBid;
    }

    public void setMaximumBid(Double maximumBid) {
        this.maximumBid = maximumBid;
    }

    public Double getMinimumBid() {
        return minimumBid;
    }

    public void setMinimumBid(Double minimumBid) {
        this.minimumBid = minimumBid;
    }
}
