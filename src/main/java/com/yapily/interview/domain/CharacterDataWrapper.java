package com.yapily.interview.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDataWrapper {
    int total;
    CharacterDataContainer data;

    public int getTotal() {
        return total;
    }

    public CharacterDataContainer getData() {
        return data;
    }

    public void setData(CharacterDataContainer data) {
        this.data = data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
