package com.springMay.sumbooks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.springMay.sumbooks.fragments.Books;
import com.springMay.sumbooks.fragments.Favorite;
import com.springMay.sumbooks.adapters.FragmentAdapter;
import com.springMay.sumbooks.R;
import com.springMay.sumbooks.fragments.Settings;
import com.springMay.sumbooks.utilities.LocaleHelper;


import static java.util.Objects.requireNonNull;

public class App extends AppCompatActivity {
    Button bye;
    EditText uName;
    EditText uPassword;
    ViewPager viewPager;

    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        bye=findViewById(R.id.btnLogout);
        uName= findViewById(R.id.tvedUsername);
        uPassword= findViewById(R.id.tvedPassword);
       //
        requireNonNull(getSupportActionBar()).hide();
        //
        navigation=findViewById(R.id.bottom_navigation_view);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //
        viewPager = findViewById(R.id.view_pager); //Init Viewpager
        setupFm(getSupportFragmentManager(), viewPager); //Setup Fragment
        viewPager.setCurrentItem(1); //Set Currrent Item When Activity Start
        viewPager.setOnPageChangeListener(new PageChange()); //Listeners For Viewpager When Page Changed
    }
    //
    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){

        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);
        Adapter.add(new Favorite(), "Page One");
        Adapter.add(new Books(), "Page Two");
        Adapter.add(new Settings(), "Page Three");
        viewPager.setAdapter(Adapter);
    }
    //
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener

            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Menu menu = navigation.getMenu();
            menu.getItem(0).setIcon(R.drawable.ic_bookmark);
            menu.getItem(1).setIcon(R.drawable.ic_bookshelf);
            menu.getItem(2).setIcon(R.drawable.ic_gears);
            //
            switch (item.getItemId()) {

                case R.id.bottom_navigation_item_logs:

                    viewPager.setCurrentItem(0);
                    item.setIcon(R.drawable.ic_bookmark_colored);

                    return true;
                case R.id.bottom_navigation_item_progress:

                    viewPager.setCurrentItem(1);
                item.setIcon(R.drawable.ic_bookshelf_colored);
                    return true;
                case R.id.bottom_navigation_item_profile:

                    viewPager.setCurrentItem(2);
                   item .setIcon(R.drawable.ic_gears_colored);
                    return true;
            }

            return true;
        }
    };
    //
    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {

            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.bottom_navigation_item_logs);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.bottom_navigation_item_progress);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.bottom_navigation_item_profile);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }




    }



