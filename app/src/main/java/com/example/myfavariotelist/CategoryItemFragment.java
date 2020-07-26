package com.example.myfavariotelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryItemFragment extends Fragment {


    private static final String CATEGORY_ARGS = "category_args";
    private RecyclerView itemRecyclerView;

    Category category;

    public CategoryItemFragment() {

    }


    public static CategoryItemFragment newInstance(Category category) {

        CategoryItemFragment categoryItemFragment=new CategoryItemFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(CATEGORY_ARGS,category);
        categoryItemFragment.setArguments(bundle);
        return categoryItemFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null)
        category= (Category) getArguments().getSerializable(CATEGORY_ARGS);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_category_item, container, false);

        if(view!=null){

            itemRecyclerView=view.findViewById(R.id.item_recycler_view);
            itemRecyclerView.setAdapter(new Item_Adpater(category));
            itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }

        return view;
    }


    public void addItemToCategory(String item){

        category.getItem().add(item);
        Item_Adpater item_adpater=(Item_Adpater) itemRecyclerView.getAdapter();
        item_adpater.setCategory(category);
        item_adpater.notifyDataSetChanged();

    }

}