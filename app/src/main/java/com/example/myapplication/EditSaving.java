package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import Database.DBhelper;
public class EditSaving extends AppCompatActivity {
    Button update;
    EditText eSName, eSDescription, eSAmount;
    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_saving);
        eSName = findViewById(R.id.editText1);
        eSDescription =  findViewById(R.id.edit_description1);
        eSAmount =  findViewById(R.id.amount_Text1);
        update =  findViewById(R.id.save_btn1);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eSName.length()== 0){
                    eSName.setError("Enter Saving Name");
                }
                else if (eSDescription.length()== 0){
                    eSDescription.setError("Enter Description");
                }
                else if (eSAmount.length()== 0){
                    eSAmount.setError("Enter Target Amount");
                }
                else {
                    //db.updateSaving();
                    Toast.makeText(EditSaving.this,"Update Successfully!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
