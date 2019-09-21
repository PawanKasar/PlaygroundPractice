package com.example.test;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.example.test.Frgaments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    /**
     * ToolBar
     * */
    public static TextView titleMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleMain = (TextView)toolbar.findViewById(R.id.titleMain);
        openNewActivityOrFragment();
    }

    public void openNewActivityOrFragment(){
        homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,new HomeFragment())
                .commit();
    }
}
