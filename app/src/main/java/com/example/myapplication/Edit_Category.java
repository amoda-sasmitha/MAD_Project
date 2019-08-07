package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Edit_Category extends AppCompatActivity {

    private EditText categoryName,categoryType, categoryDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

       Intent intent = getIntent();

        categoryName = (EditText)findViewById(R.id.editText);
        categoryDescription =(EditText)findViewById(R.id.edit_description);
        //categoryType = (EditText)findViewById(R.id.income_btn_radio);

        if(intent.getExtras()!=null){
            Bundle data_bundle = intent.getExtras();
            categoryName.setText( data_bundle.getString("category_name"));
            categoryDescription.setText(data_bundle.getString("categoryDescription"));

        }

    }



}
