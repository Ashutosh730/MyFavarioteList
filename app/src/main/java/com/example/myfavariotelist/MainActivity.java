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
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnCategoryInteractionListener {

    private static final int MAIN_ACTIVITY_REQUEST_CODE = 1000;

    public static final String CATEGORY_OBJECT_KEY="CATEGORY_KEY";
    private CategoryFragment categoryFragment;
    private boolean isTablet=false;
    private CategoryItemFragment categoryItemFragment;
    FloatingActionButton fab;
    private FrameLayout categoryItemFragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryFragment=(CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.category_fragment);
        categoryItemFragmentContainer =findViewById(R.id.category_item_fragment_container);
        isTablet=categoryItemFragmentContainer!=null;

        fab=findViewById(R.id.fab);
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
                categoryFragment.giveCategoryToManager(category);

                dialogInterface.dismiss();
                displayItem(category);
            }
        });

        alertBuilder.create().show();
    }

    private void displayItem(Category category){

        if(!isTablet) {

            Intent ItemIntent = new Intent(this, CategoryItemActivity.class);
            ItemIntent.putExtra(CATEGORY_OBJECT_KEY, category);
            startActivityForResult(ItemIntent, MAIN_ACTIVITY_REQUEST_CODE);
        }
        else{

            if(categoryItemFragment!=null){

                getSupportFragmentManager().beginTransaction().remove(categoryItemFragment).commit();
                categoryItemFragment=null;

            }

            setTitle(category.getName());
            categoryItemFragment=CategoryItemFragment.newInstance(category);
            if(categoryItemFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.category_item_fragment_container,categoryItemFragment).addToBackStack(null).commit();
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayCreateCategoryItemDialog();
                }


            });
        }

    }

    private void displayCreateCategoryItemDialog() {

        final EditText itemEditTxt =new EditText(this);
        itemEditTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(this).setTitle("Enter the Item name..").setView(itemEditTxt)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String item =itemEditTxt.getText().toString();
                        categoryItemFragment.addItemToCategory(item);
                        dialogInterface.dismiss();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==MAIN_ACTIVITY_REQUEST_CODE&&resultCode==MainActivity.RESULT_OK){
            if(data!=null){
                categoryFragment.saveCategory((Category)data.getSerializableExtra(CATEGORY_OBJECT_KEY));

            }
        }

    }


    @Override
    public void CategoryIsTapped(Category category) {
            displayItem(category);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setTitle(getString(R.string.app_name));

        if(categoryItemFragment.category!=null){

            categoryFragment.getManager().saveCategory(categoryItemFragment.category);

        }

        if(categoryItemFragment!=null){

            getSupportFragmentManager().beginTransaction().remove(categoryItemFragment).commit();
            categoryItemFragment=null;

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCreateCategoryItemDialog();
            }
        });

    }
}