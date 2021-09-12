package com.yapily.interview.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class CharacterDataContainer {
    int offset;
    int limit;
    int total;
    int count;
    Character[] results;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() { return total; }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public Character[] getResults() {
        return results;
    }

    public void setResults(Character[] result) {
        this.results = result;
    }

}
