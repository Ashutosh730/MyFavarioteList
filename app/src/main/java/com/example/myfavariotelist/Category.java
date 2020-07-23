package com.example.myfavariotelist;


import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    private String name;
    private ArrayList<String> item=new ArrayList<>();

    public Category(String name, ArrayList<String> item) {
        this.name = name;
        this.item = item;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getItem() {
        return item;
    }


    public void setItem(ArrayList<String> item) {
        this.item = item;
    }
}
