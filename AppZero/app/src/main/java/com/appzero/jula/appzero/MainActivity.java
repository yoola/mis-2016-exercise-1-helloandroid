package com.appzero.jula.appzero;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void getUrl(View v) {

        BufferedReader buffer = null;
        String response = "";
        HttpURLConnection myURLConnection = null;
        //TextView tv1 = (TextView) findViewById(R.id.textView);
        EditText url_ = (EditText)findViewById(R.id.url1);
        String newUrl = url_.getText().toString();
        InputStream in = null;




        final MainActivity a = this;
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                //open connection using urlconnection and build the string from inputstream


                HttpURLConnection myURLConnection = null;
                //TextView tv1 = (TextView) findViewById(R.id.textView);
                EditText url_ = (EditText) findViewById(R.id.url1);
                String newUrl = url_.getText().toString();
                String total = null;


                try {


                    URL myURL = new URL(newUrl);

                    myURLConnection = (HttpURLConnection) myURL.openConnection();
                    InputStream in = myURLConnection.getInputStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    total = new String();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total+=line;
                    }


                }
                catch (MalformedURLException e) {

                    Toast.makeText(getApplicationContext(), "Connection fault: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {

                    Toast.makeText(getApplicationContext(), "Cannot open url: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }

                final String test = total;

                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WebView webV = (WebView) findViewById(R.id.webView1);
                        webV.getSettings().setJavaScriptEnabled(true);
                        webV.loadData(test, "text/html; charset=UTF-8", null);
                    }
                });
            }
        });

        th.start();



        }
}
