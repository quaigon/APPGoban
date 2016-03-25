package com.quaigon.kamil.goban.gobanlogic;

public class Field {
    private int x;
    private int y;
    private Stone stone;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Field(Field field) {
        this.x = field.x;
        this.y = field.y;
        this.stone = field.stone;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public Stone getStone() {
        return stone;
    }

    public boolean isEmpty() {
        if (null == stone) return true;
        return false;
    }


    public boolean compare(Field field) {
        if (this.x != field.getX()) return false;
        if (this.y != field.getY()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "x:" + x + "y:" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (x != field.x) return false;
        return y == field.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
