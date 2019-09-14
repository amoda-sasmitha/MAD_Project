package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import Database.DBhelper;
import Models.CategoryModel;

public class AddEditCategory extends AppCompatActivity {

    EditText name, description;
    RadioButton SelectedBtn;
    RadioGroup radioGroup;
    ImageView icon;
    DBhelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);

        name = findViewById(R.id.editText);
        description = findViewById(R.id.edit_description);
        icon = findViewById(R.id.category_icon);
        radioGroup = findViewById(R.id.radio);

        db = new DBhelper(this);


    }

    public void addCategory(View view) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        SelectedBtn = findViewById( selectedId );

        CategoryModel category = new CategoryModel();
        category.setName( name.getText().toString().trim() );
        category.setDescription( description.getText().toString().trim() );
        category.setIcon( (String) icon.getTag() );
        category.setType( SelectedBtn.getText().toString().trim() );


        boolean result = db.insertCategory( category);

        //inflate layout
        View layout = getLayoutInflater().inflate( R.layout.toast_message , (ViewGroup) view.findViewById(R.id.toastRoot) );
        TextView text = layout.findViewById(R.id.textMsg);
        CardView background = layout.findViewById(R.id.back);

        //creat toast
        Toast toast = new Toast( this );
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );


        if( result == false ){
            text.setText("Category Added Unsuccessfully");
            background.setCardBackgroundColor( getResources().getColor(R.color.red));
            text.setTextColor( getResources().getColor( R.color.white ));
            toast.setView(layout);
            toast.show();


        }else{
            background.setCardBackgroundColor( getResources().getColor(R.color.green));
            text.setTextColor( getResources().getColor( R.color.white ));
            text.setText("Category Added Successfully");
            toast.setView(layout);
            this.finish();
            toast.show();

        }



    }

}
