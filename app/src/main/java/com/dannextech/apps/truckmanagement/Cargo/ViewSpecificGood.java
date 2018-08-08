package com.dannextech.apps.truckmanagement.Cargo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dannextech.apps.truckmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSpecificGood extends Fragment {


    public ViewSpecificGood() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_specific_good, container, false);

        return view;
    }

}
