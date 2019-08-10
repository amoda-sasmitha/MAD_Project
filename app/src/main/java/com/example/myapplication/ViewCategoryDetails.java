package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewCategoryDetails extends AppCompatActivity {

    private ImageButton edit_categorybtn, delete_btn;
    private TextView categoryName , description, category_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_details);

        delete_btn = findViewById(R.id.delete_btn);
        edit_categorybtn = (ImageButton) findViewById(R.id.edit_btn);
        edit_categorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit_categorybtn= findViewById(R.id.edit_btn);
                categoryName = findViewById(R.id.category_text);
                description = findViewById(R.id.category_view_description);
               category_type = findViewById(R.id.categoryType);
                Intent intent = new Intent(ViewCategoryDetails.this , Edit_Category.class);

                Bundle bundle = new Bundle();
               bundle.putString("CategoryType",category_type.getText().toString());
                bundle.putString("category_name",categoryName.getText().toString());
               bundle.putString("categoryDescription",description.getText().toString());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog( ViewCategoryDetails.this );
                dialog.setContentView(R.layout.delete_message);
                Button accept = dialog.findViewById(R.id.accept_btn);
                TextView textView = dialog.findViewById(R.id.deleteText);
                ImageButton close = dialog.findViewById(R.id.close_btn);
                textView.setText("Are you sure , you want to delete this category ?");
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
                        Intent intent = new Intent( ViewCategoryDetails.this , MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });


    }




}
