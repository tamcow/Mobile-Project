package com.example.cellphonetiu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import com.example.cellphonetiu.Adapter.ViewPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemID = item.getItemId();
                if (itemID == R.id.menu_Home){
                    viewPager.setCurrentItem(0);
                }else if (itemID == R.id.menu_Favorite){
                    viewPager.setCurrentItem(1);
                }else if (itemID == R.id.menu_Cart){
                    viewPager.setCurrentItem(2);
                }else if (itemID == R.id.menu_Profile){
                    viewPager.setCurrentItem(3);
                }
//                switch (item.getItemId()){
//                    case R.id.menu_Home:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.menu_Favorite:
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case R.id.menu_Cart:
//                        viewPager.setCurrentItem(2);
//                        break;
//                    case R.id.menu_Profile:
//                        viewPager.setCurrentItem(3);
//                        break;
//                }
                return true;
            }
        });
    }
}