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
        // button is pressed

        final MainActivity a = this;
        hideSoftKeyboard(a); // hide keyboard when button is pressed

        /* Prevent app being frozen when loading stuff
         * by using threads in the background
         */
        Thread th = new Thread(new Runnable() {
            @Override public void run() {
                EditText url_ = (EditText) findViewById(R.id.url1);
                String newUrl = url_.getText().toString();
                String total = new String();


                /* Used webpages for the code block below:
                 * http://stackoverflow.com/questions/3961589/android-webview-and-loaddata
                 * http://stackoverflow.com/questions/8654876/http-get-using-android-httpurlconnection
                 * http://stackoverflow.com/questions/2492076/android-reading-from-an-input-stream-efficiently
                 */

                try{
                    //open connection using urlconnection and build the string from inputstream
                    URL myURL = new URL(newUrl);
                    HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                    InputStream in = myURLConnection.getInputStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    total = new String();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total += line;
                    }
                } catch (MalformedURLException e) {
                    a.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            Toast.makeText(getApplicationContext(), "Connection fault! Invalid Url!", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                    a.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            Toast.makeText(getApplicationContext(), "Cannot open url! Check your connection or spelling!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                // display the web page
                final String data = total;
                a.runOnUiThread(new Runnable() {
                    @Override public void run() {
                        WebView webV = (WebView) findViewById(R.id.webView1);
                        webV.getSettings().setJavaScriptEnabled(true);
                        webV.loadData(data, "text/html; charset=UTF-8", null);
                    }
                });
            }

        });

        th.start();

    }
}
