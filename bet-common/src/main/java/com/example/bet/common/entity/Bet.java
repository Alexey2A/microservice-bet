package com.example.bet.common.entity;

import javax.persistence.*;

@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long betId;

    @ManyToOne
    @JoinColumn(name = "fork_id")
    private Fork fork;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column
    private String bet;

    @Column
    private double coefficient;

    public Bet() {

    }

    public Bet(Long betId, Fork fork, Event event, String bet, double coefficient) {
        this.betId = betId;
        this.fork = fork;
        this.event = event;
        this.bet = bet;
        this.coefficient = coefficient;
    }

    public Long getBetId() {
        return betId;
    }

    public Fork getFork() {
        return fork;
    }

    public Event getEvent() {
        return event;
    }

    public String getBet() {
        return bet;
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "bet_id=" + betId +
                ", fork_id=" + fork +
                ", event_id=" + event +
                ", bet=" + bet +
                ", coefficient=" + coefficient +
                '}';
    }
}

