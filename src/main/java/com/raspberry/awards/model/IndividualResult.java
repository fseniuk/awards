package com.raspberry.awards.model;

public record IndividualResult(String producer, Integer interval, Integer previousWin, Integer followingWin) implements Comparable<IndividualResult> {

    @Override
    public int compareTo(IndividualResult ir) {
        int intervalCompare = this.interval.compareTo(ir.interval());
        if(intervalCompare != 0) {
            return intervalCompare;
        }
        else if(this.previousWin.compareTo(ir.previousWin()) != 0) {
            return this.previousWin.compareTo(ir.previousWin());
        }
        else if(this.followingWin.compareTo(ir.followingWin()) != 0) {
            return this.followingWin.compareTo(ir.followingWin());
        }
        return this.producer.compareTo(ir.producer());
    }
}
