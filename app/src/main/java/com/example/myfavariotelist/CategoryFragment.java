package com.example.myfavariotelist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements Category_Adpater.CategoryIsClicked{


    private RecyclerView recyclerView;
    private Manager manager;




    interface OnCategoryInteractionListener{

        void CategoryIsTapped(Category category);

    }

    private OnCategoryInteractionListener listenerObject;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OnCategoryInteractionListener){

            listenerObject=(OnCategoryInteractionListener) context;
            manager=new Manager(context);

        }else{

            throw new RuntimeException("Hey,the context or activity must be implement the oncategorylistener ");

        }
    }

    public CategoryFragment() {

    }


    public static CategoryFragment newInstance() {

        return new CategoryFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_category, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Category> categories=manager.retrive();
        if(getView()!=null) {

            recyclerView = getView().findViewById(R.id.Recycler_view);
            recyclerView.setAdapter(new Category_Adpater(categories, this));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        listenerObject=null;
    }


    @Override
    public void categoryIsClicked(Category category) {

        listenerObject.CategoryIsTapped(category);

    }


    public void giveCategoryToManager(Category category){

        manager.saveCategory(category);

        Category_Adpater category_adpater=(Category_Adpater) recyclerView.getAdapter();
        category_adpater.addCategory(category);

    }

    public void saveCategory(Category category)
    {
        manager.saveCategory(category);
        updateRecyclerView();
    }

    private void updateRecyclerView() {

        ArrayList<Category> categories= manager.retrive();
        recyclerView.setAdapter(new Category_Adpater(categories,this));

    }

}