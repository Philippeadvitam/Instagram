package com.codepath.instagram.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;

/**
 * Created by Lin on 12/2/15.
 */
public class SearchUsersResultFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    public static Fragment newInstance() {
        SearchUsersResultFragment searchFragment = new SearchUsersResultFragment();
        return searchFragment;
    }
}
