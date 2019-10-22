package com.springMay.sumbooks.fragments;


import android.annotation.SuppressLint;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.springMay.sumbooks.Models.BooksModel;
import com.springMay.sumbooks.R;
import com.springMay.sumbooks.adapters.BooksRecycleAdapter;
import com.springMay.sumbooks.interfaces.InterfaceWS;

import com.springMay.sumbooks.webServices.WS;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class Books extends Fragment implements InterfaceWS {


    public Books() {
        // Required empty public constructor
    }
    private RecyclerView rec;
    private SearchView searchView;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_books, container, false);
        // initialize the recycler
        rec= v.findViewById(R.id.myRecyclerView1);


        //initialize the array



        //Define the layout of class
        rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


      //  searchView=v.findViewById(R.id.searchView);
       // imgBtnSearch=v.findViewById(R.id.imageButton);
        searchView= v.findViewById(R.id.searchView);
        WS ws= new WS();

              //  WS ws= new WS();
             ws.search(getActivity(),searchView.getQuery().toString(),Books.this);

      //  ws.search(getActivity(),searchView.getText().toString(),this);

        return v;
    }
    //
    @Override
    public void getData(final ArrayList<BooksModel> books) {
        final WS ws= new WS();
         BooksRecycleAdapter a=new BooksRecycleAdapter(getActivity(), books);
         rec.setAdapter(a);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {




                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                BooksRecycleAdapter a =new BooksRecycleAdapter(getActivity(),books);
                a.getFilter().filter(s);

                ws.search(getActivity(),searchView.getQuery().toString(),Books.this);
                return false;
            }
        });




    }
}


