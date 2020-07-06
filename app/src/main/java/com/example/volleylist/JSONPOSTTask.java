package com.example.volleylist;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONPOSTTask extends AsyncTask<String , String , String> {
    Context context;
    ShowData data;
    String fullResult;

    public JSONPOSTTask(Context mcontext , ShowData showData){
        context = mcontext;
        data = showData;
    }

    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection connection;
        BufferedWriter writer;

        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("title" , "Zohaad")
                    .appendQueryParameter("body" , "Ali");

            OutputStream outputStream = connection.getOutputStream();

            writer = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
            writer.write(builder.build().getEncodedQuery());
            writer.flush();
            writer.close();
            outputStream.close();

            connection.connect();
//
//            InputStream inputStream = connection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//           String s = reader.readLine();
//           reader.close();
            return builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        this.fullResult = result;
//        data.onPostComplete(result);
//    }
}
