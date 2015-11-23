package com.quaigon.kamil.goban;


import java.util.HashSet;

public class Group {
    private HashSet<Field> fields;
    private HashSet<Field> liberties;
    private int color;

    public Group(Field field) {
        this.fields = new HashSet<>();
        this.liberties = new HashSet<>();
        this.color = field.getStone().getColor();
        this.fields.add(field);
    }

    public Group (Group group) {
        this.fields = new HashSet<>();
        this.liberties = new HashSet<>();
        this.copyGroup(group);
    }


    public void addStones(Group group) {
        this.fields.addAll(group.getFields());
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public void addLiberty (Field field) {
        liberties.add(field);
    }

    public HashSet<Field> getFields () {
        return fields;
    }

    public int getColor() {
        return color;
    }


    public int getLibertiesCount () {
        int borderCount= 0;
        for (Field f : fields) {
            if (f.getX()==19 || f.getX() == 1) borderCount++;
            if (f.getY()==19 || f.getY() == 1) borderCount++;

        }
        return liberties.size()-borderCount;
    }

    public HashSet<Field> getLiberties() {
        return liberties;
    }


    public void copyGroup (Group group) {
        for (Field f : group.getFields()) {
            this.addField(new Field(f));
        }
        for (Field f : group.getLiberties()) {
            this.addLiberty(new Field(f));
        }
        this.color = group.color;
    }


}


