package com.example.shopgame.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopgame.R;
import com.example.shopgame.databinding.ActivityCustomerInformationBinding;
import com.example.shopgame.utils.CheckConnection;
import com.example.shopgame.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerInformationActivity extends AppCompatActivity {

    private ActivityCustomerInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            setListener();
        } else {
            CheckConnection.showToast(getApplicationContext(), String.valueOf(R.string.check_network));
        }
    }

    private void setListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nameCus = binding.edtNameCus.getText().toString().trim();
                final String sdt = binding.edtPhone.getText().toString().trim();
                final String email = binding.edtEmail.getText().toString().trim();
                if (nameCus.length() > 0 && sdt.length() > 0 && email.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathOrder,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String iddonhang) {
                                    if (Integer.parseInt(iddonhang) > 0) {
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest request = new StringRequest(Request.Method.POST, Server.pathOrderDetail,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.equals("1")) {
                                                            MainActivity.cartArrayList.clear();
                                                            CheckConnection.showToast(getApplicationContext(), "Bạn Đã Đặt Hàng Thành Công");
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            startActivity(intent);
                                                            CheckConnection.showToast(getApplicationContext(), "Mời Bạn Tiếp Tục Mua Hàng");
                                                        } else {
                                                            Log.d("AAA", response.toString());
                                                            CheckConnection.showToast(getApplicationContext(), "Dữ Liệu Giỏ Hàng Bị Lỗi");
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
                                                JSONArray jsonArray = new JSONArray();
                                                for (int i = 0; i < MainActivity.cartArrayList.size(); i++) {
                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("iddonhang", iddonhang);
                                                        jsonObject.put("idgame", MainActivity.cartArrayList.get(i).getIdGame());
                                                        jsonObject.put("tengame", MainActivity.cartArrayList.get(i).getGame());
                                                        jsonObject.put("giagame", MainActivity.cartArrayList.get(i).getPriceGame());
                                                        jsonObject.put("soluonggame", MainActivity.cartArrayList.get(i).getQuantity());

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(jsonObject);
                                                }
                                                HashMap<String, String> param = new HashMap<String, String>();
                                                param.put("json", String.valueOf(jsonArray));
                                                return param;
                                            }
                                        };
                                        queue.add(request);
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
                            param.put("tenkhachhang", nameCus);
                            param.put("sdt", sdt);
                            param.put("email", email);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.showToast(getApplicationContext(), "Hãy Kiểm Tra Lại Dữ Liệu Nhập Vào");
                }
            }
        });
    }
}