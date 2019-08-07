package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewCategoryDetails extends AppCompatActivity {

    private ImageButton edit_categorybtn;
    private TextView categoryName , description, category_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_details);


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

    }




}
