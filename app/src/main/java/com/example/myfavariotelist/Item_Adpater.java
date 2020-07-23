package com.example.myfavariotelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Item_Adpater extends RecyclerView.Adapter<Item_view_holder> {


    private Category categories;

    public Item_Adpater(Category category) {
        categories = category;
    }

    @NonNull
    @Override
    public Item_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder,parent,false);
        return new Item_view_holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Item_view_holder holder, int position) {

        holder.itemTxtView.setText(String.valueOf(categories.getItem().get(position)));

    }

    @Override
    public int getItemCount() {
        return categories.getItem().size();
    }
}
