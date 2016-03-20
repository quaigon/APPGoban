package com.quaigon.kamil.goban.gametree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node {
    private Node parent;
    private List<Node> nodes;
    private HashMap info;
    private Move move;

    public Node (Node parent, Move move) {
        this.parent = parent;
        this.move = move;
        this.nodes = new ArrayList<>();
        this.info = new HashMap();
    }

    public boolean isRoot () {
        return null == parent;
    }

    public boolean hasChildren() {
        return this.nodes.size() == 0;
    }

    public Node getNode (int i) {
        return this.nodes.get(i);
    }

    public Move getMove() {
        return move;
    }

    public void addChild(Node node) {
        this.nodes.add(node);
    }

    public void addComent (String comment) {
        info.put("comment", comment);
    }

    public Node getParent() {
        return parent;
    }
}
