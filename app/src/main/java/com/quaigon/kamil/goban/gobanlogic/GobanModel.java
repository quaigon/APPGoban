package com.quaigon.kamil.goban.gobanlogic;

import android.util.Log;

import com.quaigon.kamil.goban.gametree.Move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import roboguice.util.Ln;

public class GobanModel implements GroupManagerListener {

    private Field[][] fields;
    private GroupManager groupManager;

    public GobanModel() {
        this.groupManager = new GroupManager();
        this.groupManager.setGroupManagerListener(this);
        initBoard();
    }

    public GobanModel(GobanModel gobanModel) {
        initBoard();
        copyGoban(gobanModel);
    }

    private void initBoard() {
        fields = new Field[21][21];
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                fields[i][j] = new Field(i, j);
            }
        }
    }

    public boolean isEmpty (int x, int y) {
        return fields[x][y].isEmpty();
    }

    public boolean isEmpty (Move move) {
        return isEmpty(move.getX(), move.getY());
    }

    public void copyGoban(GobanModel gobanModel) {
        initBoard();
        for (Field field : gobanModel.getNonEmptyFields()) {
            this.fields[field.getX()][field.getY()].setStone(new Stone(field.getStone().getColor()));
        }
        this.groupManager = new GroupManager(gobanModel.getGroupManager());
        this.groupManager.setGroupManagerListener(this);
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void putStone(int x, int y, int color) {
        if (fields[x][y].isEmpty()) {
            fields[x][y].setStone(new Stone(color));
            groupManager.addGroup(fields[x][y]);
        }
    }

    public void putStone (Move move) {
        int x = move.getX();
        int y = move.getY();
        int color = move.getColor();
        if (fields[x][y].isEmpty()) {
            fields[x][y].setStone(new Stone(color));
            groupManager.addGroup(fields[x][y]);
        }
    }


    public List<Field> getNonEmptyFields() {
        List<Field> nonEmptyFields = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                if (!fields[i][j].isEmpty()) {
                    nonEmptyFields.add(fields[i][j]);
                }
            }
        }
        return nonEmptyFields;
    }


    public void printGroups() {
        for (int i = 4; i < 10; i++) {
            String line = "";
            for (int j = 12; j < 17; j++) {
                Group g = groupManager.getGroup(j, i);
                if (null != g) {
                    line += System.identityHashCode(g) + "; ";
                } else {
                    line += "*********; ";
                }
            }
            Log.d("Goban", line);
        }

        Log.d("Goban", "lol");
        Log.d("Goban", "lol");
    }


    public void printStones() {
        Log.d("Goban", "[" + String.valueOf(System.identityHashCode(this)) + "]");
        for (int i = 1; i < 20; i++) {
            String line = "";
            for (int j = 1; j < 20; j++) {
                Field g = fields[j][i];
                if (null != g.getStone()) {
                    line += (g.getStone().getColor() == 1 ? 'B' : 'W') + "| ";
                } else {
                    line += "·| ";
                }
            }
            Log.d("Goban", line);
        }

        Log.d("Goban", "-----------------------------");
        Log.d("Goban", "");
    }

    public void addSGFMove(String cords) {
        Ln.d(cords);
        char color = cords.charAt(0);
        char x = cords.charAt(2);
        char y = cords.charAt(3);
        switch (color) {
            case 'W':
                putStone(Character.getNumericValue(x) - Character.getNumericValue('a') + 1, Character.getNumericValue(y) - Character.getNumericValue('a') + 1, 0);
                break;
            case 'B':
                putStone(Character.getNumericValue(x) - Character.getNumericValue('a') + 1, Character.getNumericValue(y) - Character.getNumericValue('a') + 1, 1);
                break;
        }
    }



    @Override
    public void onGroupRemoved(HashSet<Field> fields) {
        StringBuilder message = new StringBuilder("Removing: ");
        for (Field field : fields) {
            message.append(field);
            message.append(", ");
            this.fields[field.getX()][field.getY()].setStone(null);
        }
        Log.w("Goban", message.toString());
    }
}