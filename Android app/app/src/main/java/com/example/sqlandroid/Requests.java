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
    /*
    protected  get_values()
    {
        JSONObject jObj = new JSONObject(result);
        JSONArray jArray =jObj.getJSONArray("Users");
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject json_data = jArray.getJSONObject(i);
            Users user = new Users();
            user.setId(json_data.getInt("id"));
            user.setName(json_data.getString("name"));
            user.setUsername(json_data.getString("username"));
            user.setEmail(json_data.getString("email"));
            users.add(user);
        }
    }

     */
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
                JSONArray jArray =json.getJSONArray("response");
                ArrayList<String> list = (ArrayList<String>) jArray.getJSONObject(0).keys();
              return list;
            }

        return null;
    }


}




