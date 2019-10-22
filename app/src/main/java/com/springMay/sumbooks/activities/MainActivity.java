package com.springMay.sumbooks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.springMay.sumbooks.R;

import com.springMay.sumbooks.utilities.Constants;
import com.springMay.sumbooks.utilities.General;
import com.springMay.sumbooks.utilities.LocaleHelper;
import com.springMay.sumbooks.webServices.WS;


import static java.util.Objects.*;

public class MainActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    TextInputEditText etName;
    TextInputLayout textInputLayout2;
    TextInputEditText etPassword;
    CheckBox cbRm;
    TextView tvR;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the action bar
        requireNonNull(getSupportActionBar()).hide();

        //  Hide the status bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // set up
        SharedPreferences userSettings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean x= userSettings.getBoolean("remember me",false);
        if (x){


            finish();
        startActivity(new Intent(this,App.class));



        }

       // initialize
        textInputLayout = findViewById(R.id.textInputLayout);
        etName = findViewById(R.id.etName);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        etPassword = findViewById(R.id.etPassword);
        cbRm = findViewById(R.id.checkBox);
        tvR = findViewById(R.id.tvfoot);
        btn = findViewById(R.id.button);

        // set up a spannable string
        //1// determine the string
        SpannableString ss = new SpannableString(getResources().getString(R.string.you_don_t_have_account_yet_register_here));
        //2// make the string clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
                //3// determine where to go once click it
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        //4// determine specific words to be clickable
        ss.setSpan(clickableSpan, 28, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), 28, 41,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

       //5// link the configuration with TextView
        tvR.setText(ss);
        tvR.setMovementMethod(LinkMovementMethod.getInstance());
        tvR.setHighlightColor(Color.TRANSPARENT);
        

    }
    public void login(View view) {

        if (TextUtils.isEmpty(etName.getText())) {

            etName.setError(getResources().getString(R.string.required));
            etName.requestFocus();

            return;
        }

        if (TextUtils.isEmpty(etPassword.getText())) {

            etPassword.setError(getResources().getString(R.string.required));
            etPassword.requestFocus();

            return;
        }


        if (!General.checkLength(etName.getText().toString(), Constants.CHECK_NAME))
        {
            etName.setError("short username");
        }

        if (!General.checkLength(etPassword.getText().toString(), Constants.CHECK_PASS))
        {
            etPassword.setError("short password");
        }


        WS loginObj= new WS();
        loginObj.logIn(this,cbRm,etName.getText().toString(),etPassword.getText().toString());


    }






}



