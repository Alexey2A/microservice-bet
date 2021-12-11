package com.example.bet.common.entity;

import javax.persistence.*;

@Entity
public class Bookmaker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookmakerId;
    @Column(columnDefinition="TEXT")
    private String name;
    @Column(columnDefinition="TEXT")
    private String url;

    public Bookmaker() {

    }

    public Bookmaker(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Long getBookmakerId() {
        return bookmakerId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Bookmaker{" +
                "bookmaker_id=" + bookmakerId +
                ", name='" + name + '\'' +
                ", link='" + url + '\'' +
                '}';
    }
}
