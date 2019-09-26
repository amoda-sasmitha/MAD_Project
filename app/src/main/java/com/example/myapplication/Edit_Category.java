package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBhelper;
import Models.CategoryModel;

public class Edit_Category extends AppCompatActivity {

     EditText categoryName,categoryDescription,categoryType;
     RadioButton radioButton;
     RadioGroup radioGroup;
     Button saveBtn;
     ImageView categoryIcon;
     DBhelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

       Intent intent = getIntent();
       final CategoryModel category = (CategoryModel) intent.getSerializableExtra("Category");

       db = new DBhelper(this);
       saveBtn = findViewById(R.id.save_btn);
       categoryName = findViewById(R.id.editText);
       categoryDescription = findViewById(R.id.edit_description);
       radioGroup = findViewById(R.id.radio);
       categoryIcon = findViewById(R.id.category_icon);

       categoryName.setText(category.getName());
       categoryDescription.setText(category.getDescription());
       if( category.getType() == "Income"){
           radioGroup.check(R.id.income_btn_radio);
       }else{
           radioGroup.check(R.id.expenses_btn_radio);
       }
        categoryIcon.setImageResource( getResources().getIdentifier( category.getIcon()  , "drawable", getPackageName() ));


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (categoryName.length() == 0) {
                    categoryName.setError("Enter Category");
                } else   if(categoryDescription.length() == 0){
                    categoryDescription.setError("Enter Description");
                }
                else{
                CategoryModel newCategory = new CategoryModel();
                newCategory.setID(category.getID());
                newCategory.setName(categoryName.getText().toString().trim());
                newCategory.setDescription(categoryDescription.getText().toString());
                int selectedBtn = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedBtn);
                newCategory.setType(radioButton.getText().toString().trim());


            boolean result = db.updateCategory(newCategory);

            View layout = getLayoutInflater().inflate(R.layout.toast_message, (ViewGroup)view.findViewById(R.id.toastRoot));
                TextView text = layout.findViewById(R.id.textMsg);
                CardView background = layout.findViewById(R.id.back);

                //creat toast
                Toast toast = new Toast( getApplicationContext() );
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );

            if (result == false){
                text.setText("Category Update Unsuccessfully");
                background.setCardBackgroundColor( getResources().getColor(R.color.red));
                text.setTextColor( getResources().getColor( R.color.white ));
                toast.setView(layout);
                toast.show();
            }else{
                background.setCardBackgroundColor( getResources().getColor(R.color.green));
                text.setTextColor( getResources().getColor( R.color.white ));
                text.setText("Category Update Successfully");
                toast.setView(layout);
                toast.show();
                Intent intent = new Intent( Edit_Category.this , MainActivity.class );
                intent.putExtra( "Category" , newCategory );
                startActivity(intent);
                finish();
            }

            }
            }
        });

    }



}
