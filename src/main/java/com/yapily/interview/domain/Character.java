package com.yapily.interview.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Character {
    int id;
    String name;
    String description;
    Image thumbnail ;

    public Character() {
        super();
    }

    public Character(int id,String name,String description) {
        this.id = id;
        this.description = description;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

}
