package com.yapily.interview.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Key {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;
    String type;
    String public_key;
    String private_key;

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublic_key() {
        return public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

}
