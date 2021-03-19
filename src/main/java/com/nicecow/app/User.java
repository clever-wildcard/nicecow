package com.nicecow.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    private
    @GeneratedValue @Id Long id;
    private String email;
    private String password;

    public User() {}
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() { return this.id; }

    public String getEmail() { return this.email; }

   public void setId(Long id) {this.id = id;}

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {  return this.password; }

    public void setPassword(String password) { this.password = password; }
}
