package com.example.sqlandroid;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;


public class Requests {
    //http://10.0.2.2
    public String connect_(String host, String port, String folder) throws IOException, JSONException {
        URL url = new URL("http://"+host+":"+port+folder);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return  content.toString();
        } else {
            return "GET request failed with status code: " + status;
        }

    }
    public ArrayList parser(String response, String type) throws JSONException {

        JSONObject json = new JSONObject(response);
        if (type.equals("message"))
        {
            String values=json.getString("response");
            values=values.replaceAll("[\\[\\](){}\"]","");
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(values.split(",")));
            return  list;

        }
        else if (type.equals("table")) {
            ArrayList<String> list = new ArrayList<String>();
            JSONArray jArray = json.getJSONArray("response");
            JSONObject exemplar = jArray.getJSONObject(0);
            Iterator<String> keys = exemplar.keys();
            while (keys.hasNext())
            {
                String key=keys.next();
                list.add(key);
            }
            return list;
        }

        return null;
    }


}




