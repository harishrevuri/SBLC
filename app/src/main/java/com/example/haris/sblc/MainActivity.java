package com.example.haris.sblc;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.haris.sblc.fragments.CandyFragment;
import com.example.haris.sblc.fragments.CottonFragment;
import com.example.haris.sblc.fragments.OtherFragment;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        CottonFragment.OnFragmentInteractionListener,
        CandyFragment.OnFragmentInteractionListener,
        OtherFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.action_title);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                showCottonFragment();
                return true;
            case R.id.navigation_dashboard:
                showCandyFragment();
                return true;
            case R.id.navigation_notifications:
                showOtherFragment();
                return true;
        }
        return false;
    }

    void showCottonFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, CottonFragment.newInstance())
                .commit();
    }

    void showCandyFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, CandyFragment.newInstance())
                .commit();
    }

    void showOtherFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, OtherFragment.newInstance())
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //ToDo
    }
}
