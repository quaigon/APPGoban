package com.quaigon.kamil.goban.gametree;

import com.quaigon.kamil.goban.gobanlogic.GobanModel;

/**
 * Created by Kamil on 23.03.2016.
 */
public interface GameTreeManager  {

    GobanModel getNextState();

    GobanModel getPrevState();

    GobanModel getLast();

    String getComment();

    void putMove(Move move);

    int getMoveNo ();

    boolean hasNext();

}
