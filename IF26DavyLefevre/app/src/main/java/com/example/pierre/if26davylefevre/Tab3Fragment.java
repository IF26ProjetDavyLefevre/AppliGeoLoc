package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class Tab3Fragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_tab3_fragment, container, false);
        TextView textview = (TextView) view.findViewById(R.id.tabtextview);

        return view;
    }
}