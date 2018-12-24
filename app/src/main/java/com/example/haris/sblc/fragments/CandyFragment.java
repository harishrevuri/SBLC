package com.example.haris.sblc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.haris.sblc.R;


public class CandyFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText price_cotton;
    private View btn_submit;

    public CandyFragment() {
    }

    public static CandyFragment newInstance() {
        CandyFragment fragment = new CandyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_candy, container, false);
        price_cotton = rootView.findViewById(R.id.price_cotton);
        btn_submit = rootView.findViewById(R.id.btn_submit);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataValid()) {
                    double newPrice = Double.parseDouble(
                            price_cotton.getText().toString()
                    );
                    mListener.requestPriceSet(newPrice);
                }
            }
        });
    }

    private boolean dataValid() {
        if (price_cotton.getText().toString().trim().length() == 0) {
            price_cotton.setError(getString(R.string.err_msg_invalid));
            price_cotton.requestFocus();
            return false;
        }
        return true;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void requestPriceSet(double price);
    }
}
