package com.mickhearne.irishbutterflies.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mickhearne.irishbutterflies.R;
import com.mickhearne.irishbutterflies.utilities.AnalyticsData;


/**
 * Created by michaelhearne on 22/04/2014.
 */
public class FeedbackFragment extends DialogFragment {


    public static FeedbackFragment newInstance(int title, int message, int positive, int negative, int icon) {
        FeedbackFragment f = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        args.putInt("positive", positive);
        args.putInt("negative", negative);
        args.putInt("icon", icon);
        f.setArguments(args);
        return f;
    }


    public interface DialogClickListener {
        void onPositiveClick();
        void onNegativeClick();
    }


    DialogClickListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt("title");
        int message = getArguments().getInt("message");
        int positive = getArguments().getInt("positive");
        int negative = getArguments().getInt("negative");
        int icon = getArguments().getInt("icon");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setIcon(icon)
                .setMessage(message)
                .setPositiveButton(positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onPositiveClick();
                            }
                        })
                .setNegativeButton(negative,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onNegativeClick();
                            }
                        }
                ).create();
    }


    @Override
    public void onStart() {
        super.onStart();

        // Google Analytics
        AnalyticsData.sendWithScreenName("Feedback Screen");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogClickListener");
        }
    }
}