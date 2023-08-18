package com.example.sqlandroid;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;

public class Database_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String host = getIntent().getStringExtra("host");
        String pwd = getIntent().getStringExtra("pwd");
        String port = getIntent().getStringExtra("port");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_screen);
        TextView text = findViewById(R.id.textView);
        ListView listView = findViewById(R.id.list);
        text.setText(host + ":" + port);
        new RequestTask().execute(host, port);

    }

    public void Table_Intent(String db_name) {
        Intent intent = new Intent(this, Table.class);
        intent.putExtra("host", getIntent().getStringExtra("host"));
        intent.putExtra("port", getIntent().getStringExtra("port"));
        intent.putExtra("db_name", db_name);
        startActivity(intent);

    }

    class RequestTask extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... data) {
            Requests obj = new Requests();

            try {
                String response = obj.connect_(data[0], data[1], "/get_list");
                return obj.parser(response, "message");
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Database_screen.this, android.R.layout.simple_list_item_1, result);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Table_Intent(selectedItem);
            });
        }
    }


}