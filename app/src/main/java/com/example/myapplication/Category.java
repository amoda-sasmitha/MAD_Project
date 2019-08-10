package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Category extends Fragment {

    private FloatingActionButton plusBtn;
    private RelativeLayout selectbtn;
    private TextView categoryText;
    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category ,container , false);
        addNewCategory(view);
        selectbtn = view.findViewById(R.id.category01);

         bundle = getArguments();
        boolean fromExpenses = bundle.getBoolean("FromExpenses");

        if( fromExpenses == true  ){
           selectbtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   categoryText = view.findViewById(R.id.category_text);

                   String category = categoryText.getText().toString();


                   bundle.putString("CategoryName" , category);
                   AddExpense expense = new AddExpense();
                   expense.setArguments(bundle);
                   getFragmentManager().beginTransaction().replace(R.id.fragment_container , expense).commit();


               }
           });


        }else{

            selectbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intend = new Intent(getActivity(), ViewCategoryDetails.class);
                    startActivity(intend);
                }
            });

        }

        return view;
    }

    public void addNewCategory(View view){
        plusBtn = view.findViewById(R.id.add_category_btn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditCategory.class );
                startActivity(intent);
            }
        });


    }
}
