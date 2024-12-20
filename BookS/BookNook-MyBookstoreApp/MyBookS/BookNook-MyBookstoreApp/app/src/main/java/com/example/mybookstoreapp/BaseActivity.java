package com.example.mybookstoreapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);

        AppCompatDelegate.setDefaultNightMode(
                nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.appbar);

            View actionBarView = actionBar.getCustomView();

            ImageView back = actionBarView.findViewById(R.id.left_icon);
            ImageView cart = actionBarView.findViewById(R.id.cart);
            ImageView mode = actionBarView.findViewById(R.id.mode);
            ImageView logout = actionBarView.findViewById(R.id.logout);

            back.setOnClickListener(view -> finish());

            cart.setOnClickListener(view -> {
                startActivity(new Intent(this, CartActivity.class));
            });

            logout.setOnClickListener(view -> {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
            });

            mode.setOnClickListener(view -> {
                editor = sharedPreferences.edit();
                if (nightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("night", true);
                }
                editor.apply();
                recreate();
            });
        }
    }
}
