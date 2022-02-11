package com.example.shopgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopgame.R;
import com.example.shopgame.activity.CartActivity;
import com.example.shopgame.activity.MainActivity;
import com.example.shopgame.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Cart> cartArrayList;

    public CartAdapter(Context context, ArrayList<Cart> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtGame, txtPriceGame;
        Button btnAdd, btnSubtract, btnValues;
        ImageView imgGame;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_cart, null);
            holder = new ViewHolder();

            holder.imgGame = (ImageView) view.findViewById(R.id.imgGame);
            holder.txtGame = (TextView) view.findViewById(R.id.txtGame);
            holder.txtPriceGame = (TextView) view.findViewById(R.id.txtPriceGame);
            holder.btnAdd = (Button) view.findViewById(R.id.btnAdd);
            holder.btnSubtract = (Button) view.findViewById(R.id.btnSubtract);
            holder.btnValues = (Button) view.findViewById(R.id.btnValues);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Cart cart = cartArrayList.get(i);
        holder.txtGame.setText(cart.getGame());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceGame.setText("Giá : " + decimalFormat.format(cart.getPriceGame()) + " Đ");
        holder.btnValues.setText(cart.getQuantity() + "");
        Picasso.get().load(cart.getImgGame())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgGame);

        int quantity = Integer.parseInt(holder.btnValues.getText().toString());
        if (quantity >= 10) {
            holder.btnAdd.setVisibility(View.INVISIBLE);
            holder.btnSubtract.setVisibility(View.VISIBLE);
        } else if (quantity <= 1) {
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.btnSubtract.setVisibility(View.INVISIBLE);
        } else {
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.btnSubtract.setVisibility(View.VISIBLE);
        }

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = Integer.parseInt(holder.btnValues.getText().toString()) + 1;
                int nowQuantity = MainActivity.cartArrayList.get(i).getQuantity();
                long nowPrice = MainActivity.cartArrayList.get(i).getPriceGame();

                MainActivity.cartArrayList.get(i).setQuantity(newQuantity);
                long newPrice = (nowPrice * newQuantity) / nowQuantity;
                MainActivity.cartArrayList.get(i).setTotalPriceGame(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.txtPriceGame.setText("Giá : " + decimalFormat.format(newPrice) + " Đ");
                CartActivity.setPriceGameForCart();

                if (newQuantity > 9) {
                    holder.btnAdd.setVisibility(View.INVISIBLE);
                } else {
                    holder.btnAdd.setVisibility(View.VISIBLE);
                }
                holder.btnSubtract.setVisibility(View.VISIBLE);
                holder.btnValues.setText(newQuantity + "");
            }
        });

        holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = Integer.parseInt(holder.btnValues.getText().toString()) - 1;
                int nowQuantity = MainActivity.cartArrayList.get(i).getQuantity();
                long nowPrice = MainActivity.cartArrayList.get(i).getPriceGame();

                MainActivity.cartArrayList.get(i).setQuantity(newQuantity);
                long newPrice = (nowPrice * newQuantity) / nowQuantity;
                MainActivity.cartArrayList.get(i).setTotalPriceGame(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.txtPriceGame.setText("Giá : " + decimalFormat.format(newPrice) + " Đ");
                CartActivity.setPriceGameForCart();

                if (newQuantity < 2) {
                    holder.btnSubtract.setVisibility(View.INVISIBLE);
                } else {
                    holder.btnSubtract.setVisibility(View.VISIBLE);
                }
                holder.btnAdd.setVisibility(View.VISIBLE);
                holder.btnValues.setText(newQuantity + "");
            }
        });

        return view;
    }
}
