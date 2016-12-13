package com.example.meduzzka.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This class represents a behavior or a portion of user interface in an Activity
 *
 * Created by Olga Bila
 */
public class CommentsFragmentProfile extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private String mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommentsFragmentProfile() {
    }

    /**
     * The system calls this when creating the fragment
     *
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     *                           onCreate method of every Android Activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            /** Load the dummy content specified by the fragment arguments*/
            mItem = getArguments().getString(ARG_ITEM_ID);
            Activity activity = this.getActivity();
        }
    }

    /**
     * The system calls this when it's time for the fragment to draw its user interface
     * for the first time.
     *
     * @param inflater object of LayoutInflater for rendering XML layout
     * @param container a bucket description for Views that wrap dynamic content
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     *                           this method
     * @return rootView is View from that is the root of your fragment's layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comments_profile, container, false);

        /** Show the dummy content as text in a TextView*/
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.comments)).setText(mItem);
        }
        return rootView;
    }
}
