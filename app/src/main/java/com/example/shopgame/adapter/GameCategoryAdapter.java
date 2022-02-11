package com.example.shopgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopgame.R;
import com.example.shopgame.model.GameCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameCategoryAdapter extends BaseAdapter {

    ArrayList<GameCategory> gameCategoryArrayList;
    Context context;

    public GameCategoryAdapter(ArrayList<GameCategory> gameCategoryArrayList, Context context) {
        this.gameCategoryArrayList = gameCategoryArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gameCategoryArrayList.size();
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
        TextView txtGameCategory;
        ImageView imgGameCategory;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_game_category, null);
            holder = new ViewHolder();

            holder.txtGameCategory = (TextView) view.findViewById(R.id.txtGameCategory);
            holder.imgGameCategory = (ImageView) view.findViewById(R.id.imgGameCategory);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        GameCategory category = gameCategoryArrayList.get(i);
        holder.txtGameCategory.setText(category.getGameCategoryName());
        Picasso.get().load(category.getGameCategoryImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgGameCategory);

        return view;
    }
}
