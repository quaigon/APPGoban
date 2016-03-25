package com.quaigon.kamil.goban.gametree;

import com.quaigon.kamil.goban.Goban;

/**
 * Created by Kamil on 23.03.2016.
 */
public interface GameTreeManager  {

    Goban getNextState();

    Goban getPrevState();

    Goban getLast();

    String getComment();

    void putMove(Move move);

    int getMoveNo ();

}
