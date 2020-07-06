//package com.example.volleylist;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import org.json.JSONObject;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
//public class HttpPostAsyncTask extends AsyncTask<String , String , String> {
//    JSONObject jsonObject;
//    movingToNewActivity newActivity;
//    Context context;
//    String fullResult;
//    HashMap<String,String> postData;
//
//    public HttpPostAsyncTask(Context mcontext , movingToNewActivity data , HashMap<String , String> postData){
//        context = mcontext;
//        newActivity = data;
//        if (postData!=null){
//            this.jsonObject = new JSONObject(postData);
//        }
//        this.postData = postData;
//
//    }
//    @Override
//    protected String doInBackground(String... params) {
//        try {
//            URL url = new URL(params[0]);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//         //   connection.setRequestProperty("Content-Type","application/json");
//            connection.setRequestMethod("POST");
////            if (this.jsonObject!=null){
//            String postParams = getPostDataString(postData);
//                OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
//                streamWriter.write(postParams);
//                streamWriter.flush();
//                int statusCode = connection.getResponseCode();
//                if (statusCode == 200){
//                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuffer buffer = new StringBuffer();
//                    String line = "";
//                    while ((line = reader.readLine())!=null){
//                        buffer.append(line);
//                    }
//                     return buffer.toString();
//                }
//      //      }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//        }
//
//        return result.toString();
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        this.fullResult = result;
////        newActivity.onPostComplete(result);
//    }
//}
