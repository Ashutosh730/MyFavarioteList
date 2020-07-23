package com.example.myfavariotelist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Item_view_holder extends RecyclerView.ViewHolder{


    public TextView itemTxtView;

    public Item_view_holder(@NonNull View itemView) {
        super(itemView);

        itemTxtView=itemView.findViewById(R.id.item_txt_view);
    }


}
