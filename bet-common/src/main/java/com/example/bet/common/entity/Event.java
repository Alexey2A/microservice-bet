package com.example.bet.common.entity;

import javax.persistence.*;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="event_id")
    private Long eventId;

    @ManyToOne
    @JoinColumn(name="bookmaker_id")
    private Bookmaker bookmaker;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @Column
    private String name;

    public Event() {

    }

    public Event(Long eventId, Bookmaker bookmaker, Game game, String name) {
        this.eventId = eventId;
        this.bookmaker = bookmaker;
        this.game = game;
        this.name = name;
    }

    public Long getEventId() {
        return eventId;
    }

    public Bookmaker getBookmaker() {
        return bookmaker;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event_id=" + eventId +
                ", bookmaker_id=" + bookmaker +
                ", game_id=" + game +
                ", name='" + name + '\'' +
                '}';
    }
}
