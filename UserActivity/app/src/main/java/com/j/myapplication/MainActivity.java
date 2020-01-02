package com.j.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.j.myapplication.database.DBManager;
import com.j.myapplication.database.DatabaseHelper;
import com.j.myapplication.database.UserActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    ListAdapter adapter;
    private List<HashMap<String, String>> mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbManager = new DBManager(MainActivity.this);
        dbManager.open();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserActivities.class));

            }
        });



        adapter = new ListAdapter(this);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mResult=  dbManager.getListOfActivities();
        adapter.notifyDataSetChanged();
    }


    class ListAdapter extends BaseAdapter {

        Context mContext;


        public ListAdapter(MainActivity mainActivity) {
            mContext = mainActivity;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            if(mResult!=null){
                return mResult.size();
            }
            return 0;
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row;
            row = inflater.inflate(R.layout.adapter_view, parent, false);
            TextView title, detail_to, detail_from;
            ImageView i1;
            title = (TextView) row.findViewById(R.id.title);
            detail_from = (TextView) row.findViewById(R.id.detail_from);
            detail_to = (TextView) row.findViewById(R.id.detail_to);
            title.setText(""+mResult.get(position).get(DatabaseHelper.ACTIVITY_TYPE));
            detail_from.setText("FROM : "+mResult.get(position).get(DatabaseHelper.FROM_TIME));
            detail_to.setText("TO : "+mResult.get(position).get(DatabaseHelper.TO_TIME));

            return (row);
        }


    }

}

