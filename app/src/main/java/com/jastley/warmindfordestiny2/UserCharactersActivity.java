package com.jastley.warmindfordestiny2;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jastley.warmindfordestiny2.User.FirstCharacterFragment;
import com.jastley.warmindfordestiny2.User.SecondCharacterFragment;
import com.jastley.warmindfordestiny2.User.ThirdCharacterFragment;

public class UserCharactersActivity extends AppCompatActivity {

    private BottomNavigationView mCharacterNav;
    private FrameLayout mCharacterFrame;

    private FirstCharacterFragment firstCharacterFragment;
    private SecondCharacterFragment secondCharacterFragment;
    private ThirdCharacterFragment thirdCharacterFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_characters);

        mCharacterFrame = findViewById(R.id.character_frame);
        mCharacterNav = findViewById(R.id.character_nav);

        firstCharacterFragment = new FirstCharacterFragment();
        secondCharacterFragment = new SecondCharacterFragment();
        thirdCharacterFragment = new ThirdCharacterFragment();

        setFragment(firstCharacterFragment);
        //TODO implement OnNavigationItemReselected to refresh current fragment
        mCharacterNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_first_character_item :
                        mCharacterNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(firstCharacterFragment);
                        return true;

                    case R.id.nav_second_character_item :
                        mCharacterNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(secondCharacterFragment);
                        return true;

                    case R.id.nav_third_character_item :
                        mCharacterNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(thirdCharacterFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.character_frame, fragment);
        fragmentTransaction.commit();
    }
}
