package com.example.haris.sblc.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
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
import com.example.haris.sblc.fragments.dialogs.ChangePriceDialogFragment;

public class CottonFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView cotex_price;
    private TextView sbici_price;
    private TextView change_cotext;
    private TextView change_sbici;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.EVENT_PRICE_CHANGE_COTEX.equals(intent.getAction())) {
                double newPrice = intent.getDoubleExtra(Constants.ARG_PRICE, 0);
                if (newPrice > 0)
                    setCotexPrice(newPrice);
            } else if (Constants.EVENT_PRICE_CHANGE_SBICI.equals(intent.getAction())) {
                double newPrice = intent.getDoubleExtra(Constants.ARG_PRICE, 0);
                if (newPrice > 0)
                    setSBICIPrice(newPrice);
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
        cotex_price = rootView.findViewById(R.id.cotex_price);
        sbici_price = rootView.findViewById(R.id.sbici_price);
        change_cotext = rootView.findViewById(R.id.change_cotext);
        change_sbici = rootView.findViewById(R.id.change_sbici);

        change_cotext.setPaintFlags(change_cotext.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        change_sbici.setPaintFlags(change_sbici.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.EVENT_PRICE_CHANGE_COTEX);
        intentFilter.addAction(Constants.EVENT_PRICE_CHANGE_SBICI);
        LocalBroadcastManager.getInstance(getActivity()).
                registerReceiver(
                        mMessageReceiver,
                        intentFilter
                );

        change_cotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePriceDialogFragment.newInstance(new ChangePriceDialogFragment.OnFragmentInteractionListener() {
                    @Override
                    public void onPriceChangeRequested(double price) {
                        mListener.requestCotexPriceSet(price);
                    }
                }).show(getChildFragmentManager(), ChangePriceDialogFragment.class.getName());
            }
        });

        change_sbici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePriceDialogFragment.newInstance(new ChangePriceDialogFragment.OnFragmentInteractionListener() {
                    @Override
                    public void onPriceChangeRequested(double price) {
                        mListener.requestSBLCIPriceSet(price);
                    }
                }).show(getChildFragmentManager(), ChangePriceDialogFragment.class.getName());
            }
        });
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

    private void setCotexPrice(double newPrice) {
        if (newPrice > 0) {
            cotex_price.setText(Utils.formatPrice(newPrice));
        } else {
            cotex_price.setText(getString(R.string.str_price_na));
        }
    }

    private void setSBICIPrice(double newPrice) {
        if (newPrice > 0) {
            sbici_price.setText(Utils.formatPrice(newPrice));
        } else {
            sbici_price.setText(getString(R.string.str_price_na));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void requestCotexPriceSet(double price);

        void requestSBLCIPriceSet(double price);
    }
}
