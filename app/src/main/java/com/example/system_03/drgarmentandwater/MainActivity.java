package com.example.system_03.drgarmentandwater;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText et_firstname,et_lastname,et_email,et_pwd,et_mobile;
    Button btn;
    String firstname,lastname,email,pwd,mobile,url;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et_firstname = (EditText)findViewById(R.id.editText);
        et_lastname = (EditText)findViewById(R.id.editText2);
        et_email = (EditText)findViewById(R.id.editText3);
        et_pwd = (EditText)findViewById(R.id.editText4);
        et_mobile = (EditText)findViewById(R.id.editText5);
        btn = (Button)findViewById(R.id.btn_register);

        firstname = et_firstname.getText().toString();
        lastname = et_lastname.getText().toString();
        email = et_email.getText().toString();
        pwd = et_pwd.getText().toString();
        mobile = et_mobile.getText().toString();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Register().execute();

            }
        });
    }

    class Register extends AsyncTask<String,String,String>{

        String url = "http://blessindia.in/webservice/register.php";




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Registering...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            URL obj = null;
            try {
                obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("first_name", firstname);
                con.setRequestProperty("last_name", lastname);
                con.setRequestProperty("email", email);
                con.setRequestProperty("password", pwd);
                con.setRequestProperty("mobile", mobile);

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.flush();
                wr.close();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
        }
    }
}
