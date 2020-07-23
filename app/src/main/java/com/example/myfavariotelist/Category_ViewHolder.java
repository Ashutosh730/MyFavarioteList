package com.example.myfavariotelist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Category_ViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView number;

    public Category_ViewHolder(@NonNull View view) {
        super(view);
        name=view.findViewById(R.id.name);
        number=view.findViewById(R.id.number);

    }

    public TextView getNumber() {
        return number;
    }
    public TextView getName() {
        return name;
    }


}
