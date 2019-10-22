package com.springMay.sumbooks.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.springMay.sumbooks.R;

import com.springMay.sumbooks.activities.MainActivity;
import com.springMay.sumbooks.utilities.General;
import com.springMay.sumbooks.webServices.WS;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    public Settings() {

    }
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings, container, false);


        // Button bye;
        EditText uName = v.findViewById(R.id.tvedUsername);
        EditText uPassword = v.findViewById(R.id.tvedPassword);
        TextView id = v.findViewById(R.id.id);

        SharedPreferences userSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        WS ws= new WS();
        ws.getUserInfo(getActivity(),userSettings.getString("uName",""),userSettings.getString("uPassword",""));

        String x= userSettings.getString("uName","");
        String y= userSettings.getString("uPassword","");
        String Z= userSettings.getString("id","");

         uName.setText(x);
          uPassword.setText(y);
           id.setText(Z);


        Button logout = v.findViewById(R.id.btnLogout);


           logout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   General.clearPreference(getActivity());
                   General.goToActivity(getActivity(),MainActivity.class);
                   Objects.requireNonNull(getActivity()).finish();

               }
           });









    return v;


    }


}
