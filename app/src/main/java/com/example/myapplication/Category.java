package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Category extends Fragment {

    private FloatingActionButton plusBtn;
    private RelativeLayout ViewDetailsbtn;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category ,container , false);

        plusBtn = view.findViewById(R.id.add_category_btn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditCategory.class );
                startActivity(intent);
            }
        });
        ViewDetailsbtn = view.findViewById(R.id.category01);
        ViewDetailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intend = new Intent( getActivity() , ViewCategoryDetails.class );
                startActivity(intend);
            }
        });

        return view;
    }
}
