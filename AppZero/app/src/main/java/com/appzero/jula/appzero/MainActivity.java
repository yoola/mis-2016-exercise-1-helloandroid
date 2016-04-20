package com.appzero.jula.appzero;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public static void hideSoftKeyboard(Activity activity) {
        // http://developer.android.com/reference/android/view/inputmethod/InputMethodManager.html
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void getUrl(View v) {


        final MainActivity a = this;
        hideSoftKeyboard(a);
        // Using Thread now to do the process in the background
        // Prevents that the app is frozen when loading stuff

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                //open connection using urlconnection and build the string from inputstream

                EditText url_ = (EditText) findViewById(R.id.url1);
                String newUrl = url_.getText().toString();
                String total = null;



                //Used webpages for the code block below:
                //http://stackoverflow.com/questions/3961589/android-webview-and-loaddata
                //http://stackoverflow.com/questions/8654876/http-get-using-android-httpurlconnection
                //http://stackoverflow.com/questions/2492076/android-reading-from-an-input-stream-efficiently

                try{
                    URL myURL = new URL(newUrl);
                    HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                    InputStream in = myURLConnection.getInputStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    total = new String();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total += line;
                    }
                }
                catch (MalformedURLException e) {
                    System.out.println("Malformed URL: " + e.getMessage());
                    //Toast.makeText(getApplicationContext(), "Malformed URL: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    System.out.println("IOExeption, Could not open connection: " + e.getMessage());
                    //Toast.makeText(getApplicationContext(), "Cannot open url: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }

                // display t web page
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
