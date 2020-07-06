package com.example.volleylist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;


public class ShowData<date> extends AppCompatActivity {


    ListView listView;
    RequestQueue requestQueue;
    FloatingActionButton actionButton;
    Button button,btnDlt,btnNoti;
    String name;
    sqliteDatabase myDb;
    String Data;
    String base_URL;
    int position;
    HashMap<String , String> deleteData;
    int request_code = 01;
    JSONObject jsonObject;
    UpdateData updateData;
    ArrayList<JsonDataList> list;
    JsonDataList dataList;
    JsonListAdapter jsonListAdapter;
    String date;
    movingToNewActivity activity;

    Notification mNotifi;

//    GetDataService getDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        fetchData();
        init();
//        notifiChannel("Your Title");
        main();
        fetchingDataFromCursor();
        onClickFloatBtn();
        onItemClickdelete();
//
//        btnNoti.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                notifiChannel("Your Title");
//
//            }
//        });



        //  fetchData();
//        createPost();
//        myDb.deleteData();
    }

    public void main(){
        list = new ArrayList<>();
        Cursor allData = myDb.getAllData();
        while (allData.moveToNext()){
            String id = allData.getString(0);
            String user_id = allData.getString(1);
            String title = allData.getString(2);
            String body = allData.getString(3);
            String date = allData.getString(4);
            JsonDataList jsonData = new JsonDataList(id, user_id, title, body, date);
            list.add(jsonData);
        }
            Collections.sort(list, new Comparator<JsonDataList>() {
                @Override
                public int compare(JsonDataList jsonDataList1, JsonDataList jsonDataList2) {


                    String a = jsonDataList1.getTaskDate();
                    String b =jsonDataList2.getTaskDate();

                    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                    Date date = null;
                    Date date2 = null;

                    try {
                        date = format.parse(a);
                        date2 = format.parse(b);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    jsonListAdapter.notifyDataSetChanged();

                    return date.compareTo(date2);

                }
            });
    }



//    public void fetchData(){
//        String result = null;
//        new JSONGETTask(this,ShowData.this,updateData).execute("http://192.168.1.108:8888/android/include/v1/user.php","GET");
//    }
//
    public void onGetComplete(final String result){
        try {

                JSONArray jsonArray = new JSONArray(result);
                list = new ArrayList<>();
                for (int i = 0; i <jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String user_id = jsonObject.getString("userId");
                    String title = jsonObject.getString("title");
                    String body = jsonObject.getString("body");
                    date = jsonObject.getString("taskDate");
                    JsonDataList dataList = new JsonDataList(user_id,id,title,body,date);
                    list.add(dataList);
                }
                jsonListAdapter = new JsonListAdapter(ShowData.this,R.layout.adapter_view_layout,list);
                listView.setAdapter(jsonListAdapter);


        } catch (JSONException e) {

            e.printStackTrace();
        }

    }
    public void cancelAlarm(){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this , AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);
    }


        public void onItemClickdelete(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog dialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"Delete Task" , "Update Task"};
                builder.setTitle(list.get(position).getTitle());
                final String id = list.get(position).getUserId();
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
//                                Cursor allData = myDb.getAllData();
//                                myDb.getAllData();
//                                while (allData.moveToNext()) {
//                                    String id = allData.getString(0);
//                                    String user_id = allData.getString(1);
//                                    String title = allData.getString(2);
//                                    String body = allData.getString(3);
//                                    String date = allData.getString(4);
//                                    JsonDataList jsonData = new JsonDataList(id, user_id, title, body, date);
//                                    list.add(jsonData);
//                                }
//                                jsonListAdapter = new JsonListAdapter(ShowData.this,R.layout.activity_header,list);
//                                listView.setAdapter(jsonListAdapter);
                                Integer deleteRows = myDb.deleteRows(id);
                                if (deleteRows > 0){
                                    cancelAlarm();
                                    Toast.makeText(ShowData.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ShowData.this, "Task not Deleted", Toast.LENGTH_SHORT).show();
                                }
                                list.remove(position);
                                jsonListAdapter.notifyDataSetChanged();
                                fetchingDataFromCursor();
                                break;

                            case 1:
                                Intent intent = new Intent(ShowData.this , UpdateData.class);
                                intent.putExtra("position" , position);
                                intent.putExtra("userId" , list.get(position).getId());
                                intent.putExtra("title" , list.get(position).getTitle());
                                intent.putExtra("body" , list.get(position).getBody());
                                intent.putExtra("id" , list.get(position).getUserId());
                                intent.putExtra("date" , list.get(position).getTaskDate());
                                startActivityForResult(intent,request_code);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }
//    public void onItemClickDelete(){
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                ProgressDialog dialog = new ProgressDialog(view.getContext());
//                CharSequence[] dialogItem = {"Delete Data" , "Update Data"};
//                builder.setTitle(list.get(position).getTitle());
//                final String id = list.get(position).id;
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        switch (i){
//                            case 0:
//
//                                JSONGETTask task = new JSONGETTask(getApplicationContext(), ShowData.this,updateData);
//                                task.execute("http://192.168.1.108:8888/android/include/v1/user.php?id="+id,"DELETE");
//                                list.remove(position);
//                                jsonListAdapter.notifyDataSetChanged();
//                                fetchData();
//                                Toast.makeText(ShowData.this, "Task Deleted", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case 1:
//                                Intent intent = new Intent(ShowData.this , UpdateData.class);
//                                intent.putExtra("position" , position);
//                                intent.putExtra("id" , list.get(position).getId());
//                                intent.putExtra("title" , list.get(position).getTitle());
//                                intent.putExtra("body" , list.get(position).getBody());
//                                intent.putExtra("userId" , list.get(position).getUserId());
//                                intent.putExtra("taskDate" , list.get(position).getTaskDate());
//                                startActivityForResult(intent,request_code);
//                                break;
//                        }
//
//                    }
//                });
//                builder.create().show();
//
//            }
//
//        });
//
//    }


//
//        getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<List<JsonDataList>> call = getDataService.getAllData();
//        call.enqueue(new Callback<List<JsonDataList>>() {
//            @Override
//            public void onResponse(Call<List<JsonDataList>> call, Response<List<JsonDataList>> response) {
//                onResponseWork(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<JsonDataList>> call, Throwable t) {
//                Toast.makeText(ShowData.this, "error"+t.toString(), Toast.LENGTH_SHORT).show();
//                onFailureWork();
//            }
//        });


    public void init(){
        myDb = new sqliteDatabase(this);
//        btnDlt = (Button) findViewById(R.id.dlt_btn);
        listView = (ListView) findViewById(R.id.list_view);
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
//        btnNoti = findViewById(R.id.btn_noti);
        requestQueue = Volley.newRequestQueue(ShowData.this);
        mNotifi = new Notification(this);
        activity = new movingToNewActivity();
    }

    public void onClickFloatBtn(){

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              movingToNewActivity();

            }
        });

    }

    public void movingToNewActivity(){
        Intent intent = new Intent(ShowData.this, movingToNewActivity.class);
        startActivityForResult(intent,request_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (request_code == requestCode){
            if (resultCode == RESULT_OK){
                String upd_title = data.getStringExtra("upd_title");
                String value = data.getStringExtra("title");
                String date = data.getStringExtra("date");
                fetchingDataFromCursor();
//                Toast.makeText(ShowData.this, ""+list.get(1).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }
    }

//
//    public void onFailureWork(){
////        fetchingDataFromCursor();
//    }

//    public void onResponseWork(List<JsonDataList> data){
//        storeDataInLocalStorage(data);
//        reloadListView(data);
//    }

//    public void storeDataInLocalStorage(List<JsonDataList> data){
//        for (int i = 0; i<data.size(); i++) {
//
//            String Id = data.get(i).getId();
//            String user_id = data.get(i).getUserId();
//            String title = data.get(i).getTitle();
//            String body = data.get(i).getBody();
//
//            if (data.size()==200){
//                Toast.makeText(this, "Already Entered", Toast.LENGTH_SHORT).show();
//            }else {
//                boolean isInserted = myDb.insertData(user_id, title, body,date);
//                if (isInserted == true) {
//                    Toast.makeText(ShowData.this, "Data is Inserted", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ShowData.this, "Data is not Inserted", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//    }

//    public void reloadListView(List<JsonDataList> data) {
//        JsonListAdapter adapter = new JsonListAdapter(ShowData.this,R.layout.adapter_view_layout, data);
//        listView.setAdapter(adapter);
//    }
//
    public void fetchingDataFromCursor(){
//        myDb.deleteData();
        list = new ArrayList<JsonDataList>();
        Cursor allData = myDb.getAllData();

        if (allData.getCount() == 0) {
            Toast.makeText(ShowData.this, "Database was empty", Toast.LENGTH_SHORT).show();
        } else {
            while (allData.moveToNext()) {
                String id = allData.getString(0);
                String user_id = allData.getString(1);
                String title = allData.getString(2);
                String body = allData.getString(3);
                String date = allData.getString(4);
                JsonDataList jsonData = new JsonDataList(id, user_id, title, body, date);
                list.add(jsonData);
            }
            Collections.sort(list, new Comparator<JsonDataList>() {
                @Override
                public int compare(JsonDataList jsonDataList1, JsonDataList jsonDataList2) {


                    String a = jsonDataList1.getTaskDate();
                    String b =jsonDataList2.getTaskDate();

                    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                    Date date = null;
                    Date date2 = null;

                    try {
                        date = format.parse(a);
                        date2 = format.parse(b);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    jsonListAdapter.notifyDataSetChanged();

                    return date.compareTo(date2);

                }
            });
            jsonListAdapter = new JsonListAdapter(ShowData.this,R.layout.activity_header,list);
            listView.setAdapter(jsonListAdapter);

//            Toast.makeText(this, "Internet Failure...", Toast.LENGTH_SHORT).show();
//            reloadListView(list);
//        }
//
//
//    }
//    public void createPost(){
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                JsonDataList dataList = new JsonDataList("5","8", "My Title" , "My Body");
//
//
//                Call<JsonDataList> call = getDataService.createPost(dataList);
//                call.enqueue(new Callback<JsonDataList>() {
//                    @Override
//                    public void onResponse(Call<JsonDataList> call, Response<JsonDataList> response) {
//                        response.body();
//                        Toast.makeText(ShowData.this, "Response"+ response.body(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonDataList> call, Throwable t) {
//
//                        Toast.makeText(ShowData.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });

//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.my_menu,menu);
//        MenuItem menuItem = menu.findItem(R.id.search_icon);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Search Here!");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//}


        }
    }
}