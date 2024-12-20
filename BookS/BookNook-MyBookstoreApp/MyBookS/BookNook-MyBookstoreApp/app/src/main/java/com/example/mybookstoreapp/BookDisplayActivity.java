package com.example.mybookstoreapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookDisplayActivity extends BaseActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    MyAdapter adapter;
    ArrayList<HashMap<String, Object>> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this, items));


        // Инициализация базы данных и списка книг
        db = FirebaseFirestore.getInstance();
        items = new ArrayList<>();
        adapter = new MyAdapter(this, items);
        recyclerView.setAdapter(adapter);

        // Загрузка данных из Firestore
        loadBooks();
    }

    private void loadBooks() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Books")
                .get()
                .addOnCompleteListener(task -> {
                    Log.d("Firestore", "Fetching books...");
                    if (task.isSuccessful()) {
                        Log.d("Firestore", "Books fetched successfully: " + task.getResult().size());
                    } else {
                        Log.e("Firestore", "Error fetching books: " + task.getException().getMessage());
                    }

                });

    }
}
