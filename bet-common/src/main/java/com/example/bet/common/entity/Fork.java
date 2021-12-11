package com.example.bet.common.entity;

import javax.persistence.*;

@Entity
public class Fork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long forkId;
    @Column
    private Double margin;

    public Fork(){

    }

    public Fork(double margin) {
        this.margin = margin;
    }

    public Long getForkId() {
        return forkId;
    }

    public Double getMargin() {
        return margin;
    }

    @Override
    public String toString() {
        return "Fork{" +
                "fork_id=" + forkId +
                ", margin=" + margin +
                '}';
    }
}
