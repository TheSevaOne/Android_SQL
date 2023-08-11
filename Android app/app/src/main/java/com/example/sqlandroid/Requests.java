package com.example.sqlandroid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {
    //http://10.0.2.2
    public String connect_(String host, String port, String folder) throws IOException {

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
            return  "GET request failed with status code: " + status;
        }

    }


}




