package com.example.volleylist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Formatter;

public class movingToNewActivity extends AppCompatActivity implements android.app.TimePickerDialog.OnTimeSetListener {

    EditText editTitle,editBody , editDate,editTime;
    Button taskBtn,calenderBtn,timeBTN;
    sqliteDatabase myDb;
    DatePickerDialog.OnDateSetListener setListener;
    Context context;
    ShowData data;
    String base_URL;
    String result;
    String title,body,date,time;
    Calendar calendar;
    long id = -1;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        getSupportActionBar().setTitle("New Task");
        init();

//        Intent intent = getIntent();
//        String title = intent.getStringExtra("title");
//        editTitle.setText(title);
        insertData();
//        onPostComplete(result);

//        editTime.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onClick(View view) {
//
//                Calendar calendar1 = Calendar.getInstance();
//                int hour = calendar1.get(Calendar.HOUR);
//                int min = calendar1.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(movingToNewActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int HOUR, int MIN) {
//
//                       String time =  HOUR + ":" + MIN;
//                       editTime.setText(time);
//                    }
//                },hour,min,true);
//
//                timePickerDialog.show();
//
//            }
//        });

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerDialog();
                timePicker.show(getSupportFragmentManager(),"Time picker");

            }
        });

        timeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerDialog();
                timePicker.show(getSupportFragmentManager(),"Time picker");
            }
        });




        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(movingToNewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day +"/"+month+"/"+year;
                        editDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(movingToNewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day +"/"+month+"/"+year;
                        editDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }

//    public void alertRecieverIntent(){
//        String title;
//        Intent intent = getIntent();
//        title = intent.getStringExtra("title");
//    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int Minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,Minute);
        calendar.set(Calendar.SECOND,0);

        updateTimeText(calendar);
    }


    public void updateTimeText(Calendar c){
        String timeText = "";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        editTime.setText(timeText);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this , AlertReciever.class);
//        storingData();
        intent.putExtra("position" , 1);
        intent.putExtra("title" , title);
        intent.putExtra("body" , body);
        intent.putExtra("date" , date);
        intent.putExtra("time" , time);
        intent.putExtra("id",String.valueOf(id));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        if (c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    public void init(){
        editTitle = (EditText) findViewById(R.id.edit_title);
        editBody = (EditText) findViewById(R.id.edit_body);
        editDate = (EditText) findViewById(R.id.edit_date);
        taskBtn = (Button) findViewById(R.id.btn_task);
        calenderBtn = findViewById(R.id.btn_date);
        timeBTN = findViewById(R.id.btn_time);
        editTime = (EditText) findViewById(R.id.edit_time);
        base_URL ="http://192.168.1.108:8888/android/include/v1/user.php";
        myDb = new sqliteDatabase(this);

    }

    public void storingData(){
        title = editTitle.getText().toString();
        body = editBody.getText().toString();
        date = editDate.getText().toString();
        time = editTime.getText().toString();
        Calendar calendar = Calendar.getInstance();

    }

    public void insertData(){

        taskBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                storingData();
                if (title.length()>0){
                    if (body.length()>0){
                        if (date.length()>0){
                            if (time.length()>0){
                                id = myDb.insertData(title,body,date +" " + time);
                                if (id != -1){
                                    Toast.makeText(movingToNewActivity.this, "Data is Inserted ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("id",String.valueOf(id));
                                    intent.putExtra("title" , title);
                                    intent.putExtra("body" , body);
                                    intent.putExtra("date" , date);
                                    intent.putExtra("time" , time);
                                    startAlarm(calendar);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }else {
                                    Toast.makeText(movingToNewActivity.this, "Data not Inserted ", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                editTime.setError("Select Time");
                            }
                        }else {
                            editDate.setError("Select Date");
                        }
                    }else {
                        editBody.setError("Enter Task");
                    }
                }else {
                    editTitle.setError("Enter Task");
                }

            }
        });

    }
}
