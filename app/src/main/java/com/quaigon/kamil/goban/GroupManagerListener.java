package com.quaigon.kamil.goban;

import java.util.HashSet;
import java.util.List;

public interface GroupManagerListener {
    void onGroupRemoved(HashSet<Field> fields);

    void onGroupAdded(List<Field> fields);
}
