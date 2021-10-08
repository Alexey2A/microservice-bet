package com.example.parserhockey.entity;

import com.example.parserhockey.service.ForkServiceHockey;

import javax.persistence.*;

@Entity
public class DbHockey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition="TEXT")
    private String forkHockey;

    protected DbHockey() {
    }

    public DbHockey(String forkHockey) {
        this.id = getId();
        this.forkHockey = forkHockey;
    }

    public long getId() {
        return id;
    }

    public String getForkHockey() {
        return forkHockey;
    }

    @Override
    public String toString() {
        return "DbHockey{" +
                "forkHockey='" + forkHockey + '\'' +
                '}';
    }
}
