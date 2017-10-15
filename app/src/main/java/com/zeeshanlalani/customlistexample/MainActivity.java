package com.zeeshanlalani.customlistexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.zeeshanlalani.customlistexample.Adaptor.CustomAdaptor;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] students = getResources().getStringArray(R.array.student_names);
        String[] ids = getResources().getStringArray(R.array.student_ids);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new CustomAdaptor(this, students, ids));
    }
}
