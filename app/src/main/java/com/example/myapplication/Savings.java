package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Savings extends Fragment {
    private FloatingActionButton plusBtn;
    private RelativeLayout viewDetailsBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_savings, container, false);
         viewDetailsBtn = view.findViewById(R.id.saving01);
         plusBtn = view.findViewById(R.id.add_saving_btn);

         plusBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent( getActivity() , Add_Saving.class );
                 startActivity(intent);
             }
         });

         viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent( getActivity(), ViewSavingDetails.class);
                 startActivity(intent);
             }
         });

        return view;
    }


}
