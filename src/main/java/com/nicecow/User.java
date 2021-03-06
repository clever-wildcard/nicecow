package com.nicecow;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    private @Id
    @GeneratedValue Long id;
    private String name;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public Long getId() { return this.id; }

    public String getName() { return this.name; }

    public String setName(String name) { this.name = name; }
}
