package com.example.myfavariotelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Category_Adpater extends RecyclerView.Adapter<Category_ViewHolder> {


    interface CategoryIsClicked{
        void categoryIsClicked(Category category);
    }

    private ArrayList<Category> categories;
    private CategoryIsClicked categoryIsClicked;


    public Category_Adpater(ArrayList<Category> category,CategoryIsClicked categoryIsClicked) {
        this.categories = category;
        this.categoryIsClicked=categoryIsClicked;
    }

    @NonNull
    @Override
    public Category_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.view_holder,parent,false);
        Category_ViewHolder category_viewHolder=new Category_ViewHolder(view);
        return category_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Category_ViewHolder holder, final int position) {


        holder.getNumber().setText(Integer.toString(position+1)+" ");
        holder.getName().setText(categories.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIsClicked.categoryIsClicked(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);
        notifyItemInserted(categories.size()-1);
    }
}
