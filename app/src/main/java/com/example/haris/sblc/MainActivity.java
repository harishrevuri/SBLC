package com.example.haris.sblc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.haris.sblc.fragments.CandyFragment;
import com.example.haris.sblc.fragments.CottonFragment;
import com.example.haris.sblc.fragments.OtherFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        CottonFragment.OnFragmentInteractionListener,
        CandyFragment.OnFragmentInteractionListener,
        OtherFragment.OnFragmentInteractionListener {

    private static final String TAG = "SBLC";

    FirebaseDatabase database;
    DatabaseReference mCottonRef;
    DatabaseReference mCottonPriceCotextRef;
    DatabaseReference mCottonPriceSBICIRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.action_title);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        navigation.setSelectedItemId(R.id.navigation_home);

        initFirebase();
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        mCottonRef = database.getReference(Constants.FIREBASE_KEY_COTTON);
        mCottonPriceCotextRef = mCottonRef.child(Constants.FIREBASE_KEY_COTEX).child(Constants.ARG_PRICE).getRef();
        mCottonPriceSBICIRef = mCottonRef.child(Constants.FIREBASE_KEY_SBICI).child(Constants.ARG_PRICE).getRef();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCottonPriceCotextRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double price = dataSnapshot.getValue(Double.class);
                    notifyCottonPriceChange(Constants.EVENT_PRICE_CHANGE_COTEX, price);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        mCottonPriceSBICIRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double price = dataSnapshot.getValue(Double.class);
                    notifyCottonPriceChange(Constants.EVENT_PRICE_CHANGE_SBICI, price);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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

    void notifyCottonPriceChange(String action, double newPrice) {
        Intent intent = new Intent(action); //Constants.EVENT_PRICE_CHANGE);
        intent.putExtra(Constants.ARG_PRICE, newPrice);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void requestCotexPriceSet(double price) {
        mCottonPriceCotextRef.setValue(price);
        notifyCottonPriceChange(Constants.EVENT_PRICE_CHANGE_COTEX, price);
    }

    @Override
    public void requestSBLCIPriceSet(double price) {
        mCottonPriceSBICIRef.setValue(price);
        notifyCottonPriceChange(Constants.EVENT_PRICE_CHANGE_SBICI, price);
    }
}
