package com.zeeshanlalani.customlistexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.zeeshanlalani.customlistexample.Adaptor.CustomAdaptor;
import com.zeeshanlalani.customlistexample.Helpers.DatabaseHandler;

import com.zeeshanlalani.customlistexample.Models.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button newStudent, refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] students = getResources().getStringArray(R.array.student_names);
//        String[] ids = getResources().getStringArray(R.array.student_ids);

        listView = (ListView) findViewById(R.id.list_view);
        newStudent = (Button) findViewById(R.id.new_student);
        refresh = (Button) findViewById(R.id.refresh);

        newStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddStudent.class);
                startActivity(i);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Student> students = new DatabaseHandler(MainActivity.this).getStudents();
                listView.setAdapter(new CustomAdaptor(MainActivity.this, students));
            }
        });

        // new getData().execute();
        // listView.setAdapter(new CustomAdaptor(MainActivity.this, list));
        List<Student> students = new DatabaseHandler(this).getStudents();
        listView.setAdapter(new CustomAdaptor(MainActivity.this, students));
    }

    public class getData extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        List<Student> list = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("http://10.0.2.2:3000/api/student/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                con.setReadTimeout(10000);
                con.setConnectTimeout(10000);

                InputStream in = con.getInputStream();
                con.connect();
                BufferedReader br = null;
                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                } else {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }

                JSONObject resp = bufferToJson(br);

                // Getting JSON Array node

                JSONArray contacts = resp.getJSONArray("data");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("_id");
                    String name = c.getString("firstName");
                    Student student = new Student(0, name);
                    list.add(i, student);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();

            //listView.setAdapter(new CustomAdaptor(MainActivity.this, list));
        }

        JSONObject bufferToJson (BufferedReader br) {
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject json = new JSONObject(sb.toString());
                return json;
            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}
