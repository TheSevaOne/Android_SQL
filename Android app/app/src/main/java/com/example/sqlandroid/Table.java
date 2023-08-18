package com.example.sqlandroid;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;

public class Table extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        String db_name = getIntent().getStringExtra("db_name");
        String host = getIntent().getStringExtra("host");
        String port = getIntent().getStringExtra("port");
        TextView textView=findViewById(R.id.textView2);
        new TableTask().execute(host,port,db_name);
        textView.setText(host);

}

class TableTask  extends AsyncTask<String, Void, ArrayList> {

    @Override
    protected ArrayList doInBackground(String... data) {
        Requests get_table= new Requests();
        try {
           String response = get_table.connect_(data[0],data[1],"/"+data[2]+"/get_tab");
           get_table.parser(response,"table");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    protected void onPostExecute(ArrayList result)
    {
    }
    }

}