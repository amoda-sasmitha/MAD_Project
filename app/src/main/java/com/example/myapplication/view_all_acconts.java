package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link view_all_acconts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link view_all_acconts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_all_acconts extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all_acconts, container, false);
    }






}
