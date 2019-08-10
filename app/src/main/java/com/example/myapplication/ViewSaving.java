package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewSaving extends Fragment {
    private Button addTransaction;
    private ImageButton delete_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_view_saving, container, false);

        delete_btn = view.findViewById(R.id.delete_btn);
        addTransaction = view.findViewById(R.id.add_btn);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditExpenses.class );

                startActivity(intent);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity() );
                dialog.setContentView(R.layout.delete_message);
                Button accept = dialog.findViewById(R.id.accept_btn);
                TextView textView = dialog.findViewById(R.id.deleteText);
                ImageButton close = dialog.findViewById(R.id.close_btn);
                textView.setText("Are you sure , you want to delete this saving ?");
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity() , MainActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });


        return view;
    }


}
