package com.example.myfavariotelist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryItemActivity extends AppCompatActivity {

    private FloatingActionButton add_item_button;
    private RecyclerView item_recycler_view;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        category=(Category) getIntent().getSerializableExtra(MainActivity.CATEGORY_OBJECT_KEY);
        setTitle(category.getName());

        add_item_button=findViewById(R.id.add_item_button);
        item_recycler_view=findViewById(R.id.item_recycler_view);
        item_recycler_view.setAdapter(new Item_Adpater(category));
        item_recycler_view.setLayoutManager(new LinearLayoutManager(this));


        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayItemCreation();
            }
        });

    }

    private void displayItemCreation(){

        final EditText itemEditTxt =new EditText(this);
        itemEditTxt.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this).setTitle(R.string.itemDialogTitle).setView(itemEditTxt)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String itemName =itemEditTxt.getText().toString();
                        category.getItem().add(itemName);
                        Item_Adpater item_adpater=(Item_Adpater) item_recycler_view.getAdapter();
                        item_adpater.notifyItemInserted(category.getItem().size()-1);
                        dialogInterface.dismiss();


                    }
                }).create().show();

    }

    @Override
    public void onBackPressed() {

        Bundle bundle=new Bundle();
        bundle.putSerializable(MainActivity.CATEGORY_OBJECT_KEY,category);
        Intent intent=new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
    }

}