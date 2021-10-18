package com.codesolution.mynewapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codesolution.mynewapi.databinding.ActivityCongBinding;

public class CongActivity extends AppCompatActivity {
    ActivityCongBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongActivity.this, MainActivity.class));
            }
        });
    }
}