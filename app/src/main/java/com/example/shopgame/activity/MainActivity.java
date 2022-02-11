package com.example.shopgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopgame.R;
import com.example.shopgame.adapter.GameHotAdapter;
import com.example.shopgame.databinding.ActivityMainBinding;
import com.example.shopgame.model.Cart;
import com.example.shopgame.model.Game;
import com.example.shopgame.utils.CheckConnection;
import com.example.shopgame.utils.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private ArrayList<Game> gameArrayList;
    private GameHotAdapter gameHotAdapter;
    public static ArrayList<Cart> cartArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            setToolBar();
            setNav();
            setViewFlipper();
            setRecyclerViewGameHot();
            getDataGameHot();

        } else {
            CheckConnection.showToast(getApplicationContext(), getString(R.string.check_network));
            finish();
        }
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

    private void init() {
        if (cartArrayList != null) {

        } else {
            cartArrayList = new ArrayList<>();
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setRecyclerViewGameHot() {
        gameArrayList = new ArrayList<>();
        gameHotAdapter = new GameHotAdapter(getApplicationContext(), gameArrayList);
        binding.recyclerViewGameHot.setHasFixedSize(true);
        binding.recyclerViewGameHot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        binding.recyclerViewGameHot.setAdapter(gameHotAdapter);
    }

    private void getDataGameHot() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.pathGameHot, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        gameArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                gameArrayList.add(new Game(
                                        object.getInt("ID"),
                                        object.getString("Game"),
                                        object.getInt("PriceGame"),
                                        object.getString("ImageGame"),
                                        object.getString("DescriptionGame"),
                                        object.getInt("IDGameCategory")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        gameHotAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast(getApplicationContext(), error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void setToolBar() {
        setSupportActionBar(binding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNav() {
        binding.navHome.setNavigationItemSelectedListener(this);
        getApplicationContext();
        binding.navHome.getMenu().findItem(R.id.menu_home).setChecked(true);
    }


    private void setViewFlipper() {
        ArrayList<String> arrayAdvertisement = new ArrayList<>();
        arrayAdvertisement.add("https://photo2.tinhte.vn/data/attachment-files/2020/06/5037528_Cover_Witcher.jpg");
        arrayAdvertisement.add("https://genk.mediacdn.vn/2016/photo-0-1480928083411.jpg");
        arrayAdvertisement.add("https://gamek.mediacdn.vn/133514250583805952/2020/2/14/-15816563603192133327173.jpg");
        arrayAdvertisement.add("https://genk.mediacdn.vn/139269124445442048/2021/5/22/maxresdefault-1-16216810338771745108916.jpg");
        arrayAdvertisement.add("https://game5s.vn/wp-content/uploads/2021/06/Mortal-Kombat-11-1.jpg");

        for (int i = 0; i < arrayAdvertisement.size(); i++) {
            ImageView img = new ImageView(getApplicationContext());
            Picasso.get().load(arrayAdvertisement.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.viewFlipperHome.addView(img);
        }
        binding.viewFlipperHome.setFlipInterval(5000);
        binding.viewFlipperHome.setAutoStart(true);

        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        binding.viewFlipperHome.setInAnimation(in);
        binding.viewFlipperHome.setOutAnimation(out);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            getApplicationContext();
        } else if (id == R.id.menu_game_category) {
            Intent intent = new Intent(this, GameCategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_contact) {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_address) {
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}