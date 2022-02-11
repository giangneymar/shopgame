package com.example.shopgame.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgame.R;
import com.example.shopgame.activity.GameDetailActivity;
import com.example.shopgame.model.Game;
import com.example.shopgame.utils.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameHotAdapter extends RecyclerView.Adapter<GameHotAdapter.ItemHolder> {

    Context context;
    ArrayList<Game> gameHotArrayList;

    public GameHotAdapter(Context context, ArrayList<Game> gameArrayList) {
        this.context = context;
        this.gameHotArrayList = gameArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_game_hot, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Game game = gameHotArrayList.get(position);
        holder.txtGame.setText(game.getGame());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceGame.setText("Giá : " + decimalFormat.format(game.getPriceGame()) + " Đ");
        Picasso.get().load(game.getImgGame())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgGame);
    }

    @Override
    public int getItemCount() {
        return gameHotArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgGame;
        public TextView txtGame, txtPriceGame;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgGame = (ImageView) itemView.findViewById(R.id.imgGame);
            txtGame = (TextView) itemView.findViewById(R.id.txtGame);
            txtPriceGame = (TextView) itemView.findViewById(R.id.txtPriceGame);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GameDetailActivity.class);
                    intent.putExtra("thongtingame", gameHotArrayList.get(getLayoutPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
