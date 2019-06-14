package com.nineleaps.medecord;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class activity_display__record extends AppCompatActivity {
    TextView et;
    public static String data="",user="",pid="",fname="";
    public static String[]d;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__record);
        et=(TextView)findViewById(R.id.textView2);
        for(int i=0;i<d.length;i++) {
            et.append(d[i]+"\n");
        }
    }
    class update_data extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String res="";
            try {
                URL u1=new URL(Global.url+"update_data");
                JSONObject j=new JSONObject();
                j.put("uid",user);
                j.put("pid",pid);
                j.put("fname",fname);
                j.put("data",data);
                res=HttpClientConnection.HttpExecute(u1,j);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String status) {

            if (status.equals("ok")) {
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();

                activity_user__home.user = user;
                // startActivity(nh);
            } else if (status.equals("blocked")) {
                Toast.makeText(getApplicationContext(), "You cnt assess server", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
