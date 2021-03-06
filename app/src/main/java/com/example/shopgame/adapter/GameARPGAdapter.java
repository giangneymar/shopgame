package com.example.shopgame.adapter;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopgame.R;
import com.example.shopgame.model.Game;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameARPGAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Game> gameARPGArrayList;

    public GameARPGAdapter(Context context, ArrayList<Game> gameArrayList) {
        this.context = context;
        this.gameARPGArrayList = gameArrayList;
    }

    @Override
    public int getCount() {
        return gameARPGArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtGame, txtPriceGame, txtDesGame;
        ImageView imgGame;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_game_arpg, null);
            holder = new ViewHolder();

            holder.imgGame = (ImageView) view.findViewById(R.id.imgGame);
            holder.txtGame = (TextView) view.findViewById(R.id.txtGame);
            holder.txtPriceGame = (TextView) view.findViewById(R.id.txtPriceGame);
            holder.txtDesGame = (TextView) view.findViewById(R.id.txtDesGame);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Game game = gameARPGArrayList.get(i);
        holder.txtGame.setText(game.getGame());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceGame.setText("Gi?? : " + decimalFormat.format(game.getPriceGame()) + " ??");
        holder.txtDesGame.setMaxLines(2);
        holder.txtDesGame.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtDesGame.setText(game.getDesGame());
        Picasso.get().load(game.getImgGame())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgGame);
        return view;
    }
}
