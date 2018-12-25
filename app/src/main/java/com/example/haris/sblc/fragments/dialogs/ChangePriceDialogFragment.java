package com.example.haris.sblc.fragments.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.haris.sblc.R;
import com.example.haris.sblc.Utils;

public class ChangePriceDialogFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;

    private EditText price_cotton;
    private View btn_submit;

    public ChangePriceDialogFragment() {
        // Required empty public constructor
    }

    public static ChangePriceDialogFragment newInstance(OnFragmentInteractionListener listener) {
        ChangePriceDialogFragment fragment = new ChangePriceDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_price_dialog, container, false);
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
                Utils.hideKeyboard();
                if (dataValid()) {
                    double newPrice = Double.parseDouble(
                            price_cotton.getText().toString()
                    );
                    mListener.onPriceChangeRequested(newPrice);
                    dismiss();
                }
            }
        });

        price_cotton.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn_submit.performClick();
                    return true;
                }
                return false;
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

    public interface OnFragmentInteractionListener {
        void onPriceChangeRequested(double price);
    }
}
