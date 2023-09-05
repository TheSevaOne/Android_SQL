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

public class TableList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String host = getIntent().getStringExtra("host");
        String db_name = getIntent().getStringExtra("dbname");
        String port = getIntent().getStringExtra("port");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);
        TextView textView = findViewById(R.id.textView3);
        textView.setText(db_name);
        new TableNameTask().execute(host,port,db_name);

    }

   protected void Table_Intent(String table_name)
   {
       Intent intent = new Intent(this, Table.class);
       intent.putExtra("host", getIntent().getStringExtra("host"));
       intent.putExtra("port", getIntent().getStringExtra("port"));
       intent.putExtra("dbname",getIntent().getStringExtra("dbname"));
       intent.putExtra("table", table_name);
       startActivity(intent);
   }

    class TableNameTask extends AsyncTask<String, Void, ArrayList> {


        @Override
        protected ArrayList doInBackground(String... data) {
            Requests obj = new Requests();
            try {
                String response = obj.connect_(data[0], data[1], "/" + data[2] + "/get_tab");

                return obj.parser(response, "message");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);
            ListView listView = findViewById(R.id.list_1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TableList.this, android.R.layout.simple_list_item_1, result);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Table_Intent(selectedItem);

            });
        }
    }
}