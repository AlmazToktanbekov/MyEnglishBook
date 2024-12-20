package com.example.mybookstoreapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class BookDetailActivity extends BaseActivity {

    TextView title, author, price, overview;
    ImageView cover;
    HashMap<String, Object> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        title = findViewById(R.id.textView);
        author = findViewById(R.id.textView3);
        price = findViewById(R.id.textView6);
        overview = findViewById(R.id.textView2);
        cover = findViewById(R.id.imageView2);

        data = (HashMap<String, Object>) getIntent().getSerializableExtra("data");

        title.setText((String) data.get("Name"));
        author.setText("Author: " + data.get("Author"));
        price.setText(data.get("Price") + " Tk");
        overview.setText((String) data.get("Overview"));

        String coverUrl = (String) data.get("Cover");
        Glide.with(this).load(Uri.parse(coverUrl)).into(cover);

        Button addToCart = findViewById(R.id.AddToCart);
        addToCart.setOnClickListener(view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference ref = db.collection("Cart")
                    .document("Cart")
                    .collection(userId)
                    .document((String) data.get("Name"));

            ref.set(data)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(BookDetailActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, CartActivity.class));
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(BookDetailActivity.this, "Error: Couldn't add to cart.", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
