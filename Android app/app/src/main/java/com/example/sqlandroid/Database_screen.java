package com.example.sqlandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;


public class Database_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String host = getIntent().getStringExtra("host");
        String pwd = getIntent().getStringExtra("pwd");
        String port = getIntent().getStringExtra("port");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_screen);
        TextView text = findViewById(R.id.textView);
        text.setText(host + ":" + port);
        new RequestTask().execute();

    }

    class RequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... address) {
            Requests obj = new Requests();

            try {
                return  obj.connect_(getIntent().getStringExtra("host"), getIntent().getStringExtra("port"), "/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView text= findViewById(R.id.textView2);
            text.setText(result);
        }
    }
}