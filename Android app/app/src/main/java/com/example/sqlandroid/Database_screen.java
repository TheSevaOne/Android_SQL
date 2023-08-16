package com.example.sqlandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    class RequestTask extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... address) {
            Requests obj = new Requests();

            try {
                return  obj.connect_(getIntent().getStringExtra("host"), getIntent().getStringExtra("port"), "/get_list");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);
            ListView listView = findViewById(R.id.list);
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(Database_screen.this, android.R.layout.simple_list_item_1, result);
            listView.setAdapter(adapter);
        }
    }
}