package com.quaigon.kamil.goban;


import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    private Group[][] groups;
    private GroupManagerListener groupManagerListener;

    public GroupManager() {
        this.groups = new Group[21][21];
    }

    public GroupManager(GroupManager groupManager) {
        this();
        this.copyGroupManager(groupManager);
    }

    public void copyGroupManager(GroupManager groupManager) {
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                Group g = groupManager.getGroup(i, j);

                if (null != g && groups[i][j] == null) {
                    Group newGroup = new Group(g);
                    for (Field field : newGroup.getFields()) {
                        this.groups[field.getX()][field.getY()] = newGroup;
                    }

                }
            }
        }
    }


    public void setGroupManagerListener(GroupManagerListener groupManagerListener) {
        this.groupManagerListener = groupManagerListener;
    }

    public void addGroup(Field field) {
        Group group = new Group(field);
        int x = field.getX();
        int y = field.getY();
        this.groups[x][y] = group;


        List<Group> neighbors = new ArrayList<>();
        Group tempgroup = getNorthGroup(field);
        if (null != tempgroup) {
            neighbors.add(tempgroup);
        }
        tempgroup = getSouthGroup(field);
        if (null != tempgroup) {
            neighbors.add(tempgroup);
        }

        tempgroup = getEastGroup(field);
        if (null != tempgroup) {
            neighbors.add(tempgroup);
        }

        tempgroup = getWestGroup(field);
        if (null != tempgroup) {
            neighbors.add(tempgroup);
        }

        for (Group g : neighbors) {
            mergeGroups(getGroup(x, y), g);
        }
        validateLiberties(field);
    }


    public Group getNorthGroup(Field field) {
        if (null != groups[field.getX()][field.getY() + 1]
                && field.getStone().getColor() == groups[field.getX()][field.getY() + 1].getColor()) {
            return groups[field.getX()][field.getY() + 1];
        }

        return null;
    }

    public Group getSouthGroup(Field field) {

        if (null != groups[field.getX()][field.getY() - 1]
                && field.getStone().getColor() == groups[field.getX()][field.getY() - 1].getColor()) {
            return groups[field.getX()][field.getY() - 1];
        }
        return null;
    }

    public Group getEastGroup(Field field) {
        if (null != groups[field.getX() + 1][field.getY()]
                && field.getStone().getColor() == groups[field.getX() + 1][field.getY()].getColor()) {
            return groups[field.getX() + 1][field.getY()];
        }
        return null;
    }

    public Group getWestGroup(Field field) {
        if (null != groups[field.getX() - 1][field.getY()]
                && field.getStone().getColor() == groups[field.getX() - 1][field.getY()].getColor()) {
            return groups[field.getX() - 1][field.getY()];
        }
        return null;
    }


    public Group getGroup(int x, int y) {
        return groups[x][y];
    }


    public void addLiberties(int x, int y) {

        Group group = groups[x][y];
        group.getLiberties().clear();

        for (Field f : group.getFields()) {
            if (null == groups[f.getX() + 1][f.getY()]) {
                group.addLiberty(new Field(f.getX() + 1, f.getY()));
            }

            if (null == groups[f.getX() - 1][f.getY()]) {
                group.addLiberty(new Field(f.getX() - 1, f.getY()));
            }


            if (null == groups[f.getX()][f.getY() + 1]) {
                group.addLiberty(new Field(f.getX(), f.getY() + 1));
            }

            if (null == groups[f.getX()][f.getY() - 1]) {
                group.addLiberty(new Field(f.getX(), f.getY() - 1));
            }

        }

    }


    public void mergeGroups(Group g1, Group g2) {
        g1.addStones(g2);
        for (Field f : g2.getFields()) {
            groups[f.getX()][f.getY()] = g1;
        }

    }


    public void deleteGroup(int x, int y) {
        Group group = getGroup(x, y);

        groupManagerListener.onGroupRemoved(group.getFields());

        for (Field f : group.getFields()) {
            groups[f.getX()][f.getY()] = null;
        }
    }


    public void validateLiberties(Field field) {
        for (int i = 1; i < 20; i++) {
            Outer:
            for (int j = 1; j < 20; j++) {
                Group group = groups[i][j];

                Group groupNotToDelete = groups[field.getX()][field.getY()];

                for (Field field1 : groupNotToDelete.getFields()) {
                    if (i == field1.getX() && j == field1.getY()) continue Outer;
                }

                if (null != group) {
                    addLiberties(i, j);
                    if (0 >= group.getLibertiesCount()) {
                        deleteGroup(i, j);

                    }
                }
            }
        }
    }
}
