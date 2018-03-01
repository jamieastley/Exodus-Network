package com.jastley.warmindfordestiny2.User;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.warmindfordestiny2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdCharacterFragment extends Fragment {


    public ThirdCharacterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_character, container, false);
    }

}
