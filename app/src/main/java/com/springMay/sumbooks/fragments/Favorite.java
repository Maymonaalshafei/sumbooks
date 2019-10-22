package com.springMay.sumbooks.fragments;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.springMay.sumbooks.Models.BooksModel;
import com.springMay.sumbooks.R;

import com.springMay.sumbooks.adapters.FavoriteRecycleAdapter;
import com.springMay.sumbooks.interfaces.InterfaceWS;
import com.springMay.sumbooks.webServices.WS;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite extends Fragment implements InterfaceWS {


    public Favorite() {
        // Required empty public constructor
    }
    private RecyclerView rec;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_favorite, container, false);


        rec= v.findViewById(R.id.myRecyclerView2);


        rec.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        SharedPreferences userSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String Z= userSettings.getString("id","");
            WS ws = new WS();
            ws.getFav(getActivity(),this,Z);


        return v;

    }
    //
    @Override
    public void getData(ArrayList<BooksModel> books) {
       FavoriteRecycleAdapter a=new FavoriteRecycleAdapter(getActivity(), books);
        rec.setAdapter(a);

    }
}
