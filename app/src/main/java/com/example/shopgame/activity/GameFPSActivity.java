package com.example.shopgame.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopgame.R;
import com.example.shopgame.adapter.GameARPGAdapter;
import com.example.shopgame.adapter.GameFPSAdapter;
import com.example.shopgame.databinding.ActivityGameFpsactivityBinding;
import com.example.shopgame.model.Game;
import com.example.shopgame.utils.CheckConnection;
import com.example.shopgame.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFPSActivity extends AppCompatActivity {

    private ActivityGameFpsactivityBinding binding;
    private ArrayList<Game> gameFPSArrayList;
    private GameFPSAdapter gameFPSAdapter;
    private View footerView;
    private int page = 1;
    private boolean isLoading = false;
    private boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameFpsactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mHandler = new mHandler();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            setToolbar();
            setListViewGameFPS();
            getDataGameFPS(page);
            loadMoreData();
            setFooterView();
        } else {
            CheckConnection.showToast(getApplicationContext(), getString(R.string.check_network));
        }
    }

    private void loadMoreData() {
        binding.lvGameFPS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), GameDetailActivity.class);
                intent.putExtra("thongtingame", gameFPSArrayList.get(i));
                startActivity(intent);
            }
        });

        binding.lvGameFPS.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    GameFPSActivity.ThreadData threadData = new GameFPSActivity.ThreadData();
                    threadData.start();
                }
            }
        });
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

    private void getDataGameFPS(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = Server.pathGame + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.length() != 2) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    gameFPSArrayList.add(new Game(
                                            jsonObject.getInt("ID"),
                                            jsonObject.getString("Game"),
                                            jsonObject.getInt("PriceGame"),
                                            jsonObject.getString("ImageGame"),
                                            jsonObject.getString("DescriptionGame"),
                                            jsonObject.getInt("IDGameCategory")
                                    ));
                                }
                                gameFPSAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitData = true;
                            binding.lvGameFPS.removeFooterView(footerView);
                            CheckConnection.showToast(getApplicationContext(), "Đã Hết Game");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idtheloaigame", String.valueOf(2));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setListViewGameFPS() {
        gameFPSArrayList = new ArrayList<>();
        gameFPSAdapter = new GameFPSAdapter(getApplicationContext(), gameFPSArrayList);
        binding.lvGameFPS.setAdapter(gameFPSAdapter);
    }

    private void setFooterView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
    }

    private class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    binding.lvGameFPS.addFooterView(footerView);
                    break;
                case 1:
                    getDataGameFPS(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}