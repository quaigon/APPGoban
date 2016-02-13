package com.quaigon.kamil.sgfparser;

/**
 * Created by Kamil on 13.02.2016.
 */
public class StringSGFProvider implements SGFProvider {

    private String sgf;

    public StringSGFProvider(String sgf) {
        this.sgf = sgf;
    }

    @Override
    public String getLines() {
        return sgf;
    }
}
