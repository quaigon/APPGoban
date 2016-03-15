package com.quaigon.kamil.dto;

import java.util.List;


public class Games {
    private long count;
    private String next;
    private String previous;
    private List<Game> results;


    public long getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Game> getResults() {
        return results;
    }
}
