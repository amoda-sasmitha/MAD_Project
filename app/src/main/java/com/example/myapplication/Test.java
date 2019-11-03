package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
public class Test extends AppCompatActivity  {
    private String[] icons;
    private GridView gridView;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        icons =  getResources().getStringArray(R.array.category_Icons);
    }

}
