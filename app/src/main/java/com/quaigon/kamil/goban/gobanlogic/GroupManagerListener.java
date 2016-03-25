package com.quaigon.kamil.goban.gobanlogic;

import java.util.HashSet;

public interface GroupManagerListener {
    void onGroupRemoved(HashSet<Field> fields);

}
