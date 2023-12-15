package com.raspberry.awards.model;

public class IndividualResult implements Comparable<IndividualResult> {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

    public IndividualResult(String producer, Integer interval, Integer previousWin, Integer followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    @Override
    public int compareTo(IndividualResult ir) {
        int intervalCompare = this.interval.compareTo(ir.getInterval());
        if(intervalCompare != 0) {
            return intervalCompare;
        }
        else if(this.previousWin.compareTo(ir.getPreviousWin()) != 0) {
            return this.previousWin.compareTo(ir.getPreviousWin());
        }
        else if(this.followingWin.compareTo(ir.getFollowingWin()) != 0) {
            return this.followingWin.compareTo(ir.getFollowingWin());
        }
        return this.producer.compareTo(ir.getProducer());
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Integer getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(Integer previousWin) {
        this.previousWin = previousWin;
    }

    public Integer getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(Integer followingWin) {
        this.followingWin = followingWin;
    }
}
