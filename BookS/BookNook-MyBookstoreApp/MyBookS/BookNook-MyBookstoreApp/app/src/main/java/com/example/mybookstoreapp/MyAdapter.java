package com.example.mybookstoreapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<HashMap<String, Object>> items;

    public MyAdapter(Context context, ArrayList<HashMap<String, Object>> items) {
        this.context = context;
        this.items = items;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, Object> m = items.get(position);

        // Установка данных для TextView
        String title = (String) m.get("Name");
        String author = (String) m.get("Author");
        String price = String.valueOf(m.get("Price"));
        String coverUrl = (String) m.get("Cover");

        holder.nameView.setText(title != null ? title : "Без названия");
        holder.authorView.setText(author != null ? "Автор: " + author : "Автор не указан");
        holder.priceView.setText(price != null ? price + " сом" : "Цена не указана");

        // Загрузка изображения через Glide
        Glide.with(context)
                .load(coverUrl)
                .placeholder(R.drawable.notfound) // Замените на ваш ресурс-заглушку
                .error(R.drawable.notfound) // Замените на ваш ресурс ошибки
                .into(holder.imageView);


        // Обработка клика на элемент
        holder.v.setOnClickListener(view -> {
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("data", m);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size(); // Размер списка данных
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View v;
        ImageView imageView;
        TextView nameView, authorView, priceView, rateView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.textViewTitle);
            authorView = itemView.findViewById(R.id.textViewAuthor);
            priceView = itemView.findViewById(R.id.textViewPrice);
            rateView = itemView.findViewById(R.id.textViewRating);
        }
    }
}
