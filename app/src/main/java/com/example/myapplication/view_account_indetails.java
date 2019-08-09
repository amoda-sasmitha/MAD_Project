package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class view_account_indetails extends AppCompatActivity {
    private ImageView editBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_indetails);
        editBtn = findViewById(R.id.edit_btn02);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( view_account_indetails.this , EditAccount.class);
                startActivity(intent);
            }
        });
    }
}
