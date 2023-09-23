package com.example.padho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.padho.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    private DrawerLayout drawerLayout;
   private ActionBarDrawerToggle toggle;
   private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Button MathClass;
    private ActivityDashboardBinding binding;
//    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setListeners();
        createToolbar();

    }

    private void setListeners() {
        binding.mathClass.setOnClickListener( v -> startActivity(new Intent(Dashboard.this, Subject.class)));
    }

    private void createToolbar() {
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


}