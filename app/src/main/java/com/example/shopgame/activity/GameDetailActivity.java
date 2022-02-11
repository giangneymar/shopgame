package com.example.shopgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopgame.R;
import com.example.shopgame.databinding.ActivityGameDetailBinding;
import com.example.shopgame.model.Cart;
import com.example.shopgame.model.Game;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class GameDetailActivity extends AppCompatActivity {

    private ActivityGameDetailBinding binding;
    private int id = 0;
    private String game = "";
    private int priceGame = 0;
    private String imgGame = "";
    private String desGame = "";
    private int idGameCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();
        getGameDetail();
        setSpinner();
        setListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListener() {
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.cartArrayList.size() > 0) {
                    int quantity = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.cartArrayList.size(); i++) {
                        if (MainActivity.cartArrayList.get(i).getIdGame() == id) {
                            MainActivity.cartArrayList.get(i).setQuantity(MainActivity.cartArrayList.get(i).getQuantity() + quantity);
                            if (MainActivity.cartArrayList.get(i).getQuantity() >= 10) {
                                MainActivity.cartArrayList.get(i).setQuantity(10);
                            }
                            MainActivity.cartArrayList.get(i).setTotalPriceGame(priceGame * MainActivity.cartArrayList.get(i).getQuantity());
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int qt = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                        long totalPrice = qt * priceGame;
                        MainActivity.cartArrayList.add(new Cart(id, game, totalPrice, imgGame, qt));
                    }
                } else {
                    int quantity = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                    long totalPrice = quantity * priceGame;
                    MainActivity.cartArrayList.add(new Cart(id, game, totalPrice, imgGame, quantity));
                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setSpinner() {
        Integer[] number = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, number);
        binding.spinner.setAdapter(adapter);
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getGameDetail() {
        Game games = (Game) getIntent().getSerializableExtra("thongtingame");

        id = games.getId();
        game = games.getGame();
        priceGame = games.getPriceGame();
        desGame = games.getDesGame();
        imgGame = games.getImgGame();

        binding.txtGame.setText(game);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txtPriceGame.setText("Giá : " + decimalFormat.format(priceGame) + " Đ");
        binding.txtDesGame.setText(desGame);
        Picasso.get().load(imgGame)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(binding.imgGame);
    }
}