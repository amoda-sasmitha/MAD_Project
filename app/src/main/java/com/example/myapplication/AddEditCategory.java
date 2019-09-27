package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import Adapters.CategoryIconAdapter;
import Database.DBhelper;
import Models.CategoryModel;
public class AddEditCategory extends AppCompatActivity {
    EditText name, description;
    RadioButton SelectedBtn;
    RadioGroup radioGroup;
    ImageView icon;
    DBhelper db;
    String [] icons;
    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);

        name = findViewById(R.id.editText);
        description = findViewById(R.id.edit_description);
        icon = findViewById(R.id.category_icon);
        radioGroup = findViewById(R.id.radio);
        date = findViewById(R.id.date_view);
        db = new DBhelper(this);
        icons =  getResources().getStringArray(R.array.category_Icons);
        date.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog( AddEditCategory.this );
                dialog.setContentView(R.layout.icon_select_dialog);
                GridView gridView = dialog.findViewById(R.id.gridview);
                ImageButton close = dialog.findViewById(R.id.close_btn);
                CategoryIconAdapter categoryIconAdapter = new CategoryIconAdapter(AddEditCategory.this);
                gridView.setAdapter(categoryIconAdapter);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        icon.setImageResource( getResources().getIdentifier( icons[i] , "drawable", getPackageName()));
                        icon.setTag(icons[i] );
                        dialog.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    public void addCategory(View view) {
        if (name.length() == 0) {
            name.setError("Enter Category Name");
        } else if (description.length() == 0) {
            description.setError("Enter Description");
        } else {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            SelectedBtn = findViewById(selectedId);
            CategoryModel category = new CategoryModel();
            category.setName(name.getText().toString().trim());
            category.setDescription(description.getText().toString().trim());
            category.setIcon((String) icon.getTag());
            category.setType(SelectedBtn.getText().toString().trim());
            boolean result = db.insertCategory(category);
            //inflate layout
            View layout = getLayoutInflater().inflate(R.layout.toast_message, (ViewGroup) view.findViewById(R.id.toastRoot));
            TextView text = layout.findViewById(R.id.textMsg);
            CardView background = layout.findViewById(R.id.back);
            //creat toast
            Toast toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 230);
            if (!result) {
                text.setText("Category Added Unsuccessfully");
                background.setCardBackgroundColor(getResources().getColor(R.color.red));
                text.setTextColor(getResources().getColor(R.color.white));
                toast.setView(layout);
                toast.show();
            } else {
                background.setCardBackgroundColor(getResources().getColor(R.color.green));
                text.setTextColor(getResources().getColor(R.color.white));
                text.setText("Category Added Successfully");
                toast.setView(layout);
                this.finish();
                toast.show();
            }
        }
    }

}
