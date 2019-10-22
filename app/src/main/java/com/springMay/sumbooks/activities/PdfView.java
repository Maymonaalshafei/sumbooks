package com.springMay.sumbooks.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import com.springMay.sumbooks.R;
import com.springMay.sumbooks.utilities.Config;
import com.springMay.sumbooks.utilities.LocaleHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class PdfView extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfView = findViewById(R.id.pdfView);


        // get extra
        Intent i = this.getIntent();

        String path = (String) Objects.requireNonNull(i.getExtras()).get("path");
        path = Config.urlbooks + path;

       // pdfView.fromUri(Uri.parse(path))
             //   .enableSwipe(true)
                //.swipeHorizontal(false)
                //.enableAnnotationRendering(true)
               // .scrollHandle(new DefaultScrollHandle(this))
               // .spacing(0)
               // .load();
        new RetrievePDFStream().execute(path);


    }
    //
    @SuppressLint("StaticFieldLeak")
    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {

                URL urlx = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) urlx.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;

        }


        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).enableSwipe(true)
            .swipeHorizontal(false)
           .enableAnnotationRendering(true)
           .scrollHandle(new DefaultScrollHandle(PdfView.this))
            .spacing(0)
             .load();


        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }


}



