package com.example.shopgame.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopgame.R;
import com.example.shopgame.adapter.GameCategoryAdapter;
import com.example.shopgame.databinding.ActivityGameCategoryBinding;
import com.example.shopgame.model.GameCategory;
import com.example.shopgame.utils.CheckConnection;
import com.example.shopgame.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameCategoryActivity extends AppCompatActivity {

    private ActivityGameCategoryBinding binding;
    private ArrayList<GameCategory> gameCategoryArrayList;
    private GameCategoryAdapter gameCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();
        setListViewGameCategory();
        getDataGameCategory();
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

    private void setListener() {
        binding.lvGameCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(GameCategoryActivity.this, GameARPGActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), getString(R.string.check_network));
                        }
                        break;

                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(GameCategoryActivity.this, GameFPSActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), getString(R.string.check_network));
                        }
                        break;

                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(GameCategoryActivity.this, GamePuzzleActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), getString(R.string.check_network));
                        }
                        break;
                }
            }
        });
    }

    private void setListViewGameCategory() {
        gameCategoryArrayList = new ArrayList<>();
        gameCategoryAdapter = new GameCategoryAdapter(gameCategoryArrayList, getApplicationContext());
        binding.lvGameCategory.setAdapter(gameCategoryAdapter);
    }

    private void getDataGameCategory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.pathGameCategory, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        gameCategoryArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                gameCategoryArrayList.add(new GameCategory(
                                        jsonObject.getInt("ID"),
                                        jsonObject.getString("GameCategoryName"),
                                        jsonObject.getString("GameCategoryImage")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        gameCategoryAdapter.notifyDataSetChanged();
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
}