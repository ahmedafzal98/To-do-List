//package com.example.volleylist;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//
//class JSONGETTask extends AsyncTask<String , String , String>{
//    Context context;
//    ShowData data;
//    UpdateData updData;
//    String fullResult;
//
//
//    public JSONGETTask(Context mContext , ShowData showData , UpdateData updateData){
//        context = mContext;
//        data = showData;
//        updData = updateData;
//
//    }
//
//    @Override
//    protected String doInBackground(String... urls) {
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
////        http://192.168.1.104:8888/android/include/v1/user.php?id=56&title=Titlt updated&body=body updated&userId=6
//        try {
//            URL url = new URL(urls[0]);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod(urls[1]);
//            connection.connect();
//
//            InputStream stream = connection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(stream));
//
//            StringBuffer buffer = new StringBuffer();
//            String line = "";
//            while ((line = reader.readLine())!=null){
//                buffer.append(line);
//            }
//
//            return buffer.toString();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (connection != null){
//                connection.disconnect();
//            }
//            try {
//                if (reader != null){
//                    reader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        this.fullResult = result;
//        if(data == null){
////            updData.onUpdateCompleted();
//        }else{
//            data.onGetComplete(fullResult);
//            data.onItemClickDelete();
//        }
////        data.onGetComplete(result);
////        if(data == null){
////
////        }else{
////            data.onGetComplete(fullResult);
////        }
////        data.onGetComplete(result);
//
//    }
//
//
//}
