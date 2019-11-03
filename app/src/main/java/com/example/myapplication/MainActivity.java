package com.example.myapplication;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.widget.FrameLayout;
import Database.DBhelper;
public class MainActivity extends AppCompatActivity {
    DBhelper db;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navi_categories:
                    Category category = new Category();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putBoolean( "FromExpenses" , false);
                    category.setArguments( dataBundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, category).commit();
                    return true;
                case R.id.nav_expenses:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new Expenses()).commit();
                    return true;
                case R.id.nav_savings:
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container , new Savings() ).commit();
                    return true;
            }
            return false;
        }
    };
    private FrameLayout layout;
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        layout = findViewById(R.id.fragment_container);
        navView.setAlpha(0);
        layout.setAlpha(0);
        navView.animate().alpha(1).setDuration(300);
        layout.setTranslationY(-200);
        layout.animate().translationY(0).setDuration(800);
        layout.animate().alpha(1).setDuration(1000);
        db = new DBhelper(this);
        navView.setSelectedItemId(R.id.nav_expenses);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new Expenses()).commit();
    }
}
