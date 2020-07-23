package com.example.myfavariotelist;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.strictmode.IntentReceiverLeakedViolation;
import android.print.PrinterId;
import android.text.InputType;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Category_Adpater.CategoryIsClicked {

    private static final int MAIN_ACTIVITY_REQUEST_CODE = 1000;
    private RecyclerView recyclerView;
    private Manager manager=new Manager(this);

    public static final String CATEGORY_OBJECT_KEY="CATEGORY_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        ArrayList<Category> categories=manager.retrive();
        recyclerView=findViewById(R.id.Recycler_view);
        recyclerView.setAdapter(new Category_Adpater(categories,this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayDialog(){

        String title=getString(R.string.title);
        String button=getString(R.string.button);
        final AlertDialog.Builder alertBuilder =new AlertDialog.Builder(this);
        final EditText txt=new EditText(this);
        txt.setInputType(InputType.TYPE_CLASS_TEXT);

        alertBuilder.setTitle(title);
        alertBuilder.setView(txt);
        alertBuilder.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Category category=new Category(txt.getText().toString(),new ArrayList<String>());
                manager.saveCategory(category);

                Category_Adpater category_adpater=(Category_Adpater) recyclerView.getAdapter();
                category_adpater.addCategory(category);

                dialogInterface.dismiss();
                displayItem(category);
            }
        });

        alertBuilder.create().show();
    }

    private void displayItem(Category category){

        Intent ItemIntent=new Intent(this,CategoryItemActivity.class);
        ItemIntent.putExtra(CATEGORY_OBJECT_KEY,category);
        startActivityForResult(ItemIntent,MAIN_ACTIVITY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==MAIN_ACTIVITY_REQUEST_CODE&&resultCode==MainActivity.RESULT_OK){
            if(data!=null){
                manager.saveCategory((Category) data.getSerializableExtra(CATEGORY_OBJECT_KEY));
                updateCategories();
            }
        }

    }

    private void updateCategories() {

        ArrayList<Category> categories = manager.retrive();
        recyclerView.setAdapter(new Category_Adpater(categories,this));

    }

    @Override
    public void categoryIsClicked(Category category) {

        displayItem(category);

    }


}