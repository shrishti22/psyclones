package com.nineleaps.medecord;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class activity_user__home extends AppCompatActivity {

    public static String user="";
    TextView tv;
    Button b;
    public static ArrayList<String> pids;


    class update_data extends AsyncTask<String,Void,String> {




        @Override
        protected String doInBackground(String... params) {
            String res="";
            try {
                URL u1=new URL(Global.url+"update_data");
                JSONObject j=new JSONObject();
                j.put("uid",user);
                j.put("pid",pid);
                res=HttpClientConnection.HttpExecute(u1,j);
                Log.e("Anirudh", "doInBackground: "+res );

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }


            return res;
        }

        @Override
        protected void onPostExecute(String status){

            if(status.equals("ok"))
            {
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();

                activity_user__home.user=user;
                startActivity(next);
            }
            else  if(status.equals("blocked"))
            {
                Toast.makeText(getApplicationContext(),"You cnt assess server",Toast.LENGTH_LONG).show();


            }
            else{
                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();

            }


        }


    }

    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String pid="";
    Intent next,nextiv;
    Boolean count=false;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home);
        tv=(TextView)findViewById(R.id.tv);
        tv.setText("Welcome "+user);
        b=(Button)findViewById(R.id.btnf);
        pids = new ArrayList<String>();
        next=new Intent(this,list_options.class);
        listView = (ListView) findViewById(R.id.listview);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!count){
                    new get_patient_list().execute();
                    count=true;
                }
                else{
                    Toast.makeText(getApplicationContext(),"Already fetched updated data",Toast.LENGTH_LONG).show();
                }
//                arrayAdapter = new ArrayAdapter<String>(
//                        User_Home.this,
//                        R.layout.simlpe_list_item,
//                        R.id.textView, pids
//                );
//                listView.setAdapter(arrayAdapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        String item = parent.getItemAtPosition(position).toString();
//                        Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        });
    }
    class PerformBackgroundTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String res="";
            try {
                URL u1=new URL(Global.url+"check_alert");
                JSONObject j=new JSONObject();
                j.put("did",user);
                j.put("pid",pid);
                res=HttpClientConnection.HttpExecute(u1,j);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String status){
            if(!status.equals(""))
            {
                Toast.makeText(getApplicationContext(),"Alert",Toast.LENGTH_LONG).show();
            }
        }
    }
    class get_patient_list extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String res="";
            try {
                URL u1=new URL(Global.url+"get_patient_list_did");//_did
                JSONObject j=new JSONObject();
                j.put("did",user);
                res=HttpClientConnection.HttpExecute(u1,j);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String status){
            Toast.makeText(getApplicationContext(),"Successfully fetched data",Toast.LENGTH_LONG).show();
            String s[]=status.split("-");
            for(int i=0;i<s.length;i++)
            {
                pids.add(s[i]);
            }
            arrayAdapter = new ArrayAdapter<String>(
                    activity_user__home.this,
                    R.layout.simple_list_item,
                    R.id.textView, pids
            );
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();
                    pid=item;
                    new get_file().execute();
                    //  new PerformBackgroundTask().execute();
                }
            });
//            final Handler handler = new Handler();
//            Timer timer = new Timer();
//            TimerTask doAsynchronousTask = new TimerTask()
//            {
//                @Override
//                public void run() {
//                    handler.post(new Runnable() {
//                        public void run() {
//                            try
//                            {
//
//                                new PerformBackgroundTask().execute();
//
//                            }
//                            catch (Exception e)
//                            {
//
//                            }
//                        }
//                    });
//                }
//            };
//            timer.schedule(doAsynchronousTask, 0, 60000); //execute in every 60s*
//
        }
    }
    class get_file extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String res="";
            try {
                URL u1=new URL(Global.url+"get_file");
                JSONObject j=new JSONObject();
                j.put("did",user);
                j.put("pid",pid);
                res=HttpClientConnection.HttpExecute(u1,j);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String status){
            if(status.equals("notok"))
            {
                Toast.makeText(getApplicationContext(),"No Record Found",Toast.LENGTH_LONG).show();
            }

            else{
//              Display_Record.user=user;
//              Display_Record.pid=pid;
//              Display_Record.data=status;
//              startActivity(next);

                list_options.user=user;
                list_options.pid=pid;
                String a[]=status.split("#");
                list_options.names=a;
                startActivity(next);

            }

        }


    }
}
