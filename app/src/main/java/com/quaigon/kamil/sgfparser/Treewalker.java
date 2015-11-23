package com.quaigon.kamil.sgfparser;

import android.util.Log;

import java.util.Stack;

public class Treewalker {
    SGFnode tree;
    SGFnode current;

    public Treewalker(SGFnode tree) {
        this.tree = tree;
        this.current = tree;
    }



    public SGFnode getNextMove () {
        SGFnode temp;
        if (this.current.getChildren().size() == 0) {
            return null;
        }
        temp = current.get_child(0);
        current = temp;
        return this.current;
    }


    public SGFnode getPreviousMove() {
        this.current = this.current.get_parent();
        return this.current;
    }


    public String printCurrent () {
        StringBuffer sb = new StringBuffer();
        current.dump_properties(sb);
        return sb.toString();
    }

}
