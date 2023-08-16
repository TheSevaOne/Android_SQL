package com.example.sqlandroid;
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
import java.util.List;

public class Requests {
    //http://10.0.2.2
    public ArrayList connect_(String host, String port, String folder) throws IOException, JSONException {
        URL url = new URL("http://"+host+":"+port+folder);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        ArrayList error = new ArrayList<>();
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

            return  parser(content.toString());
        } else {
            error.add("GET request failed with status code: " + status);
            return  error;
        }

    }


    public ArrayList parser(String response) throws JSONException {
        JSONObject json = new JSONObject(response);
        String values=json.getString("response");
        values=values.replaceAll("[\\[\\](){}\"]","");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(values.split(",")));

        return  list;
    }


}




