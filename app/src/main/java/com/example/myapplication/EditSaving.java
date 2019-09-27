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

            /*boolean result = db.updateCategory(newSaving);

        View layout = getLayoutInflater().inflate(R.layout.toast_message, (ViewGroup)view.findViewById(R.id.toastRoot));
        TextView text = layout.findViewById(R.id.textMsg);
        CardView background = layout.findViewById(R.id.back);

        //creat toast
        Toast toast = new Toast( getApplicationContext() );
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );

        if (result == false){
            text.setText("Saving Update Unsuccessfully");
            background.setCardBackgroundColor( getResources().getColor(R.color.red));
            text.setTextColor( getResources().getColor( R.color.white ));
            toast.setView(layout);
            toast.show();
        }else{
            background.setCardBackgroundColor( getResources().getColor(R.color.green));
            text.setTextColor( getResources().getColor( R.color.white ));
            text.setText("Saving Update Successfully");
            toast.setView(layout);
            toast.show();
            Intent intent = new Intent( Edit_Saving.this , MainActivity.class );
            intent.putExtra( "Saving" , newSaving );
            startActivity(intent);
            finish();
        }
        */
        });
    }
}
