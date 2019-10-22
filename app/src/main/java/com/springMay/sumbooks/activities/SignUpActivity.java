package com.springMay.sumbooks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.springMay.sumbooks.R;
import com.springMay.sumbooks.utilities.Constants;
import com.springMay.sumbooks.utilities.General;
import com.springMay.sumbooks.utilities.LocaleHelper;
import com.springMay.sumbooks.webServices.WS;



import static java.util.Objects.requireNonNull;

public class SignUpActivity extends AppCompatActivity {

  //  TextInputLayout textInputLayout;
    TextInputEditText etName;
    TextInputLayout textInputLayout2;
    TextInputEditText etPassword;
    Button btnSignUp;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hid the action bar
        requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_sign_up);


        textInputLayout2 =  findViewById(R.id.textInputLayout2);
        etPassword =  findViewById(R.id.etPassword);
        btnSignUp=findViewById(R.id.button);
        tv=findViewById(R.id.tv);


        // set up a spannable string
        //1// determine the string
        SpannableString ss = new SpannableString(getResources().getString(R.string.you_already_have_accont));
        //2// make the string clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
                //3// determine where to go once click it
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        //4// determine specific words to be clickable
        ss.setSpan(clickableSpan, 0, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, 25,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //5// link the configuration with TextView
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.TRANSPARENT);



    }
    //
     public void signUp(View view) {
        if (TextUtils.isEmpty(etName.getText())) {

            etName.setError(getResources().getString(R.string.required));

            return;
        }

        if (TextUtils.isEmpty(etPassword.getText())) {

            etPassword.setError(getResources().getString(R.string.required));

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

      WS signUpToDatabase= new WS();
        signUpToDatabase.signUpToDatabase(this,etName.getText().toString(),etPassword.getText().toString());
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }




}
