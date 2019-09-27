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
import android.widget.TextView;
import android.widget.Toast;

import Database.DBhelper;
import Models.SavingModel;

public class Add_Saving extends AppCompatActivity {

    DBhelper db;
    Button save;
    EditText sName, sDiscription, sTarget, sStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__saving);

        db = new DBhelper(this);

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

                    SavingModel saving = new SavingModel();
                    saving.setSavingName( sName.getText().toString().trim() );
                    saving.setSavingDescription( sDiscription.getText().toString().trim() );
                    saving.setStartAmount( Double.valueOf( sStart.getText().toString().trim() ) );
                    saving.setTargetAmount( Double.valueOf( sTarget.getText().toString().trim() ));
                    boolean result = db.addSaving( saving );

                    //inflate layout
                    View layout = getLayoutInflater().inflate(R.layout.toast_message, (ViewGroup) findViewById(R.id.toastRoot));
                    TextView text = layout.findViewById(R.id.textMsg);
                    CardView background = layout.findViewById(R.id.back);

                    //creat toast
                    Toast toast = new Toast(Add_Saving.this);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 230);


                    if (result == false) {
                        text.setText("Saving Added Unsuccessfully");
                        background.setCardBackgroundColor(getResources().getColor(R.color.red));
                        text.setTextColor(getResources().getColor(R.color.white));
                        toast.setView(layout);
                        toast.show();


                    } else {
                        background.setCardBackgroundColor(getResources().getColor(R.color.green));
                        text.setTextColor(getResources().getColor(R.color.white));
                        text.setText("Saving Added Successfully");
                        toast.setView(layout);
                        finish();
                        toast.show();

                    }

                }

            }
        });

    }
}
