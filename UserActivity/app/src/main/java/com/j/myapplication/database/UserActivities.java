package com.j.myapplication.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.j.myapplication.MainActivity;
import com.j.myapplication.R;

import java.util.Calendar;
import java.util.List;

public class UserActivities extends AppCompatActivity implements
        View.OnClickListener  {

    private ArrayAdapter<String> itemsAdapter;
    private String activityType;
    private DBManager dbManager;
    private List<String> activityList;
    private  TextView text,from_time,from_date,to_time,to_date; Spinner type;
    private int mYear, mMonth, mDay, mHour, mMinute;
    View mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activities);
          text =  findViewById(R.id.empName);


        from_date=findViewById(R.id.from_date);
        from_time=findViewById(R.id.from_time);

        to_time = findViewById(R.id.to_time);
        to_date = findViewById(R.id.to_date);


        type = findViewById(R.id.spinner);
        dbManager = new DBManager(UserActivities.this);
        dbManager.open();
        activityList=dbManager.getUserActivities();

        itemsAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activityList);
        type.setAdapter(itemsAdapter);


        from_date.setOnClickListener(this);
        from_time.setOnClickListener(this);

        to_time.setOnClickListener(this);
        to_date.setOnClickListener(this);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityType=activityList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button dialogButton = (Button) findViewById(R.id.submit);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBManager dbManager = new DBManager(UserActivities.this);
                dbManager.open();
                dbManager.insertActiviesPeriod(text.getText().toString(),
                        from_date.getText().toString()+" : "+from_time.getText().toString(),
                        to_date.getText().toString()+" : "+to_time.getText().toString(),
                        activityType
                );
                finish();

            }
        });
        
    }

    @Override
    public void onClick(View v) {

        if (v == from_date || v==to_date) {
            mView=v;
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            ((TextView)mView).setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == from_time|| v==to_time) {

            mView=v;
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String AM_PM ;
                            if(hourOfDay < 12) {
                                AM_PM = "AM";
                            } else {
                                AM_PM = "PM";
                            }
                             ((TextView)mView).setText(hourOfDay + ":" + minute+" "+AM_PM);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }


}
