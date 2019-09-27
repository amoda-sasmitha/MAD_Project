package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ViewSavingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saving_details);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable( "Saving" , intent.getSerializableExtra("Saving") );
        ViewSaving viewSaving = new ViewSaving();
        viewSaving.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , viewSaving ).commit();
    }
}
