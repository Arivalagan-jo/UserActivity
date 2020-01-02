package com.j.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.j.myapplication.database.DBManager;
import com.j.myapplication.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.e;

public class LoginActivity extends AppCompatActivity {

    Cursor cursor;
    private String empName="",empPwd="";
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        DBManager dbManager = new DBManager(this);
        dbManager.open();
        dbManager.insertEmp();
        cursor =  dbManager.getLoginEmp();
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                empName=cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                empPwd=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PASSWORD));
            }
        }
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().trim().length()==0){
                    Toast.makeText(LoginActivity.this, "enter username", Toast.LENGTH_SHORT).show();

                }else if(password.getText().toString().trim().length()==0){
                    Toast.makeText(LoginActivity.this, "enter password", Toast.LENGTH_SHORT).show();
                }else{
                    if(username.getText().toString().equalsIgnoreCase(empName)&&
                    password.getText().toString().equalsIgnoreCase(empPwd)){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();

                    }else{
                        Toast.makeText(LoginActivity.this, "invalid username/password", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }
}
