package com.example.volleylist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateData extends AppCompatActivity implements android.app.TimePickerDialog.OnTimeSetListener {

    EditText editText_id,editText_title,editText_body,editText_userId,editText_date,editText_time;
    Button button_update;
    int position;
    ShowData data;
    String result;
    UpdateData updateData;
    Context context;
    sqliteDatabase myDb;

    String id;
    String title;
    String body;
    String userId;
    String date;
    String time;
    String datetime;

    String upd_id;
    String upd_title;
    String upd_body;
    String upd_userId;
    String upd_date;
    String upd_time;
    String upd_datetime;
    boolean value = true;
    boolean isComingFromNotification;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_activity);
         Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editText_id = (EditText) findViewById(R.id.edit_id);
        editText_title = (EditText) findViewById(R.id.edit_title);
        editText_body = (EditText) findViewById(R.id.edit_body);
        editText_userId = (EditText) findViewById(R.id.edit_userid);
        editText_date = (EditText) findViewById(R.id.edit_date);
        editText_time = (EditText) findViewById(R.id.edit_time);
        button_update = (Button) findViewById(R.id.btn_update);
        myDb = new sqliteDatabase(this);


        editText_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new TimePickerDialog();
                fragment.show(getSupportFragmentManager(),"Time Picker");
            }
        });

        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day +"/"+month+"/"+year;
                        editText_date.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        intentWork();

        editText_id.setText(id);
        editText_title.setText(title);
        editText_body.setText(body);
        editText_userId.setText(userId);

        button_update.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(UpdateData.this);
                progressDialog.setMessage("Updating");
                progressDialog.show();
                storingValues();
                boolean isUpdated = myDb.updateData(upd_id,upd_title,upd_body,upd_date + " " + upd_time);
                if (isUpdated == true){
                    Toast.makeText(UpdateData.this, "Task Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateData.this, "Task not Updated", Toast.LENGTH_SHORT).show();
                }

                updateData();
                Intent intent = new Intent();
                intent.putExtra("title" , upd_title);
                setResult(RESULT_OK,intent);
                startAlarm(UpdateData.this.calendar);
                finish();


            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfday, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfday);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        updateTimeText(calendar);

    }

    public void updateTimeText(Calendar c){
        String timeText = "";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        editText_time.setText(timeText);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this , AlertReciever.class);

        storingValues();
//        String id = editText_id.getText().toString();
//        String title = editText_title.getText().toString();
//        String body = editText_body.getText().toString();
//        String date = editText_date.getText().toString();
//        String time = editText_time.getText().toString();

        intent.putExtra("id" , upd_id);
        intent.putExtra("title" , upd_title);
        intent.putExtra("body" , upd_body);
        intent.putExtra("date" , upd_date);
        intent.putExtra("time" , upd_time);

        // Put the values intent to be received in AlaramReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
    public void storingValues(){
        upd_id = editText_id.getText().toString();
        upd_title = editText_title.getText().toString();
        upd_body = editText_body.getText().toString();
        upd_date = editText_date.getText().toString();
        upd_time = editText_time.getText().toString();
    }

    public void intentWork(){

        Intent intent = getIntent();
        intent.getExtras().getInt("position");
        id = (String) intent.getExtras().get("id");
        title = (String) intent.getExtras().get("title");
        body = (String) intent.getExtras().get("body");
        userId = (String) intent.getExtras().get("userId");
        date = (String) intent.getExtras().get("date");
        isComingFromNotification = intent.getBooleanExtra("isComingFromNotification",false);


            String[] dateTime = date.split(" ");
            datetime = dateTime[0];

            time = dateTime[1];

            editText_date.setText(datetime);
            editText_time.setText(time);

    }

    public void onUpdateCompleted(){
        finish();
    }

    public void updateData() {

        Intent intent = new Intent();
        intent.putExtra("userId", upd_userId);
        intent.putExtra("title", upd_title);
        intent.putExtra("body", upd_body);
        intent.putExtra("id", upd_id);
        intent.putExtra("date", upd_date + " " + upd_time);
        setResult(RESULT_OK, intent);
        finish();
    }


//        HashMap<String , String> postData = new HashMap<>();
//        postData.put("id" , id);
//        postData.put("title" , title);
//        postData.put("body" , body);
//        postData.put("userId" , "6");
//        postData.put("taskDate" , datetime + " " + time);
//        String params = null;
//        try {
//            params = getPostDataString(postData);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        http://192.168.1.105:8888/android/include/v1/user.php?title=Zohaad&body=Ahmed&userId=6&taskDate=23/8/2020&id=103
//        JSONGETTask task = new JSONGETTask(getApplicationContext(), data,UpdateData.this);
//        task.execute("http://192.168.1.108:8888/android/include/v1/user.php?"+params,"PATCH");


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
}
