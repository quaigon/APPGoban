package com.quaigon.kamil.connection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quaigon.kamil.goban.R;

import java.util.List;


public class GameAdapter extends BaseAdapter {


    private List<Game> games = null;
    private Context contex;
    private LayoutInflater inflater;


    public GameAdapter(List<Game> games, Context contex) {
        this.games = games;
        this.contex = contex;
        this.inflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Game getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;


        if (null == view) {
            view = inflater.inflate(R.layout.game_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        Game game = getItem(position);
        viewHolder.gameIdTextView.setText(game.getId());



        return null;
    }



    static class ViewHolder {
        private TextView gameIdTextView;

        public ViewHolder (View convertView) {
            gameIdTextView = (TextView) convertView.findViewById(R.id.gameId);
        }



    }




}
