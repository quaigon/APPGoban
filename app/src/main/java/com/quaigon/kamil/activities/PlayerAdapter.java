package com.quaigon.kamil.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quaigon.kamil.dto.player.Player;
import com.quaigon.kamil.goban.R;

import java.util.List;

public class PlayerAdapter extends BaseAdapter {

    private List<Player> playersList;
    private Context contex;
    private LayoutInflater inflater;


    public PlayerAdapter(List<Player> playersList, Context contex) {
        this.playersList = playersList;
        this.contex = contex;
        this.inflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return playersList.size();
    }

    @Override
    public Object getItem(int position) {
        return playersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (null == view) {
            view = inflater.inflate(R.layout.player_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Player player = (Player) getItem(position);
        String username = player.getUsername();
        String ranking = player.getRating();

        viewHolder.usernameTextView.setText(username);
        viewHolder.rankingTextView.setText(ranking);

        return view;
    }

    static class ViewHolder {
        private TextView usernameTextView;
        private TextView rankingTextView;

        public ViewHolder(View convertView) {
            usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
            rankingTextView = (TextView) convertView.findViewById(R.id.rankingTextView);
        }
    }
}
