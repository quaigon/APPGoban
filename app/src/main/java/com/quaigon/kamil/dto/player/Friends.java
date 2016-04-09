package com.quaigon.kamil.dto.player;

import java.util.List;

public class Friends {
    private long count;
    private String next;
    private String previous;
    private List<Player> results;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Player> getResults() {
        return results;
    }

    public void setResults(List<Player> results) {
        this.results = results;
    }
}
