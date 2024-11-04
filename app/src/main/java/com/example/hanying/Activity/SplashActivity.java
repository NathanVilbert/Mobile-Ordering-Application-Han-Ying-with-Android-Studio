package com.example.hanying.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.example.hanying.R;

public class SplashActivity extends AppCompatActivity {
    ViewPager2 mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(new ViewPagerAdapter(this));

            }
    public class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new FragmentOne();
            } else if (position == 1) {
                return new FragmentTwo();
            } else if (position == 2) {
                return new FragmentThree();
            }

            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }
}