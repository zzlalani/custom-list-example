package com.zeeshanlalani.customlistexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zeeshanlalani.customlistexample.Adaptor.CustomAdaptor;
import com.zeeshanlalani.customlistexample.Models.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddStudent extends AppCompatActivity {

    EditText txtFirstName, txtLastName;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save( txtFirstName.getText().toString(), txtLastName.getText().toString() );
            }
        });
    }

    private void save(String firstName, String lastName) {
        new getData( firstName, lastName ).execute();
    }

    public class getData extends AsyncTask<Void, Void, Void> {
        String firstName, lastName;

        public getData(String _firstName, String _lastName ) {
            firstName = _firstName;
            lastName = _lastName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("http://10.0.2.2:3000/api/student/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

                con.setDoOutput(true);
                con.setDoInput(true);
                con.setInstanceFollowRedirects(false);

                con.setReadTimeout(10000);
                con.setConnectTimeout(10000);
                String param = "firstName="+firstName+"&lastName="+lastName;

                OutputStreamWriter writer = new OutputStreamWriter(
                        con.getOutputStream());
                writer.write(param);
                writer.close();

                con.connect();

                BufferedReader br = null;
                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                } else {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }

                // Getting JSON Array node

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
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
