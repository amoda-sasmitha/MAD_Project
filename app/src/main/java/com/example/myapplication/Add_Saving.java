package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Saving extends AppCompatActivity {

    Button save;
    EditText sName, sDiscription, sTarget, sStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__saving);

        sName = (EditText) findViewById(R.id.editText);
        sDiscription = (EditText) findViewById(R.id.edit_description);
        sTarget = (EditText) findViewById(R.id.addTargetAmountTypeText);
        sStart = (EditText) findViewById(R.id.addStartAmountText);
        save = findViewById(R.id.save_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sName.length()== 0){
                    sName.setError("Enter Saving Name");
                }

                else if (sDiscription.length()== 0){
                    sDiscription.setError("Enter Description");
                }

                else if (sTarget.length()== 0){
                    sTarget.setError("Enter Target Amount");
                }

                else if (sStart.length()== 0){
                    sStart.setError("Enter Start Amount");
                }

                else if (sTarget.length() < sStart.length() ){
                    sStart.setError("Enter Correct Start Amount");
                }

                else {
                    Toast.makeText(Add_Saving.this,"Saving Successfully!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
