package com.example.shopgame.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopgame.R;
import com.example.shopgame.adapter.CartAdapter;
import com.example.shopgame.databinding.ActivityCartBinding;
import com.example.shopgame.utils.CheckConnection;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    private static ActivityCartBinding binding;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListViewCart();
        setToolbar();
        checkData();
        setLongClickItem();
        setPriceGameForCart();
        setListener();
    }

    private void setListener() {
        binding.btnContinueBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.cartArrayList.size() > 0) {
                    Intent intent = new Intent(CartActivity.this, CustomerInformationActivity.class);
                    startActivity(intent);
                } else {
                    CheckConnection.showToast(getApplicationContext(), getString(R.string.cart_empty));
                }
            }
        });
    }

    private void setLongClickItem() {
        binding.lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác Nhận Xóa Game");
                builder.setMessage("Bạn Có Chắc Muốn Xóa Game Này Không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.cartArrayList.size() <= 0) {
                            binding.txtMessage.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.cartArrayList.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            setPriceGameForCart();
                            if (MainActivity.cartArrayList.size() <= 0) {
                                binding.txtMessage.setVisibility(View.VISIBLE);
                            } else {
                                binding.txtMessage.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                setPriceGameForCart();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartAdapter.notifyDataSetChanged();
                        setPriceGameForCart();
                    }
                });
                builder.show();

                return true;
            }
        });
    }

    public static void setPriceGameForCart() {
        long totalPrice = 0;
        for (int i = 0; i < MainActivity.cartArrayList.size(); i++) {
            totalPrice += MainActivity.cartArrayList.get(i).getPriceGame();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txtValuePrice.setText(decimalFormat.format(totalPrice) + " Đ");
    }

    private void setListViewCart() {
        cartAdapter = new CartAdapter(CartActivity.this, MainActivity.cartArrayList);
        binding.lvCart.setAdapter(cartAdapter);
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

    private void checkData() {
        if (MainActivity.cartArrayList.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            binding.txtMessage.setVisibility(View.VISIBLE);
            binding.lvCart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            binding.txtMessage.setVisibility(View.INVISIBLE);
            binding.lvCart.setVisibility(View.VISIBLE);
        }
    }

}