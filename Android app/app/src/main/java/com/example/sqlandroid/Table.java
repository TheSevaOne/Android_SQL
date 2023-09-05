package com.example.sqlandroid;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Pair;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class Table extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        String table_name=getIntent().getStringExtra("table");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String db_name = getIntent().getStringExtra("dbname");
        String host = getIntent().getStringExtra("host");
        String port = getIntent().getStringExtra("port");
        TextView textView=findViewById(R.id.textView2);
        new TableTask().execute(host,port,db_name,table_name);
        textView.setText(host);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class TableTask  extends AsyncTask<String, Void, Pair<JSONArray,ArrayList>>{

        @Override
        protected Pair<JSONArray,ArrayList> doInBackground(String... data) {
            Requests get_table= new Requests();
            try {
                String response = get_table.connect_(data[0],data[1],"/"+data[2]+"/"+data[3]+"/read");
                ArrayList Keys =get_table.parser(response,"table");
                JSONArray result = new JSONObject(response).getJSONArray("response");
                return  new Pair<JSONArray,ArrayList>(result,Keys);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        protected void onPostExecute(Pair result)
        {    ArrayList<DataTableRow> rows = new ArrayList<>();
            DataTable table = findViewById(R.id.data_table);
            JSONArray RJson = (JSONArray) result.first;
            List <String> column_names = (List <String>) result.second;
            DataTableHeader.Builder header = new DataTableHeader.Builder();
            for (int i=0 ; i<column_names.size();i++)
                {
                        header.item(column_names.get(i),1);
                }

                for (int i = 0; i < RJson.length(); i++) {
                    DataTableRow.Builder row = new DataTableRow.Builder();
                    for (int j = 0; j < column_names.size(); j++) {
                        try {
                            JSONObject jsonObject = RJson.getJSONObject(i);
                            row.value(jsonObject.getString((String) column_names.get(j)));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    rows.add(row.build());
                }

            table.setHeader(header.build());
            table.setRows(rows);
            table.inflate(Table.this);

        }


    }

}
