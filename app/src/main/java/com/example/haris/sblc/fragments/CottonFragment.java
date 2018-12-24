package com.example.haris.sblc.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haris.sblc.Constants;
import com.example.haris.sblc.R;
import com.example.haris.sblc.Utils;

public class CottonFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView price_cotton;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.EVENT_PRICE_CHANGE.equals(intent.getAction())) {
                double newPrice = intent.getDoubleExtra(Constants.ARG_PRICE, 0);
                setCottonPrice(newPrice);
            }
        }
    };

    public CottonFragment() {
    }

    public static CottonFragment newInstance() {
        CottonFragment fragment = new CottonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cotton, container, false);
        price_cotton = rootView.findViewById(R.id.price_cotton);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).
                registerReceiver(
                        mMessageReceiver,
                        new IntentFilter(Constants.EVENT_PRICE_CHANGE)
                );
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity())
                .unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void setCottonPrice(double newPrice) {
        if (newPrice > 0) {
            price_cotton.setText(Utils.formatPrice(newPrice));
        } else {
            price_cotton.setText("-");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        //void onFragmentInteraction(Uri uri);
    }
}
