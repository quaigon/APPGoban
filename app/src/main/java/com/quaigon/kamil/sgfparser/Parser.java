package com.quaigon.kamil.sgfparser;

public class Parser {

    private SGFProvider provider;
    private Treewalker walker;
    private SGFnode sgFnode;

    public Parser(SGFProvider provider, Treewalker walker) {
        this.provider = provider;
        this.walker = walker;
    }


}
