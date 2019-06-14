package com.nineleaps.medecord;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etu,etp;
    Button btnl;
    String u="",p="";
    Intent next,nh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etu=(EditText)findViewById(R.id.etuid);
        etp=(EditText)findViewById(R.id.etpass);
        btnl=(Button)findViewById(R.id.btnLog);



        nh=new Intent(this,activity_user__home.class);



        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u=etu.getText().toString();
                p=etp.getText().toString();

                new login_check().execute();

            }
        });

    }

    class login_check extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String res="";
            try {
                URL u1=new URL(Global.url+"Doctor_Login_app");
                JSONObject j=new JSONObject();
                j.put("uid",u);
                j.put("pass",p);

                res=HttpClientConnection.HttpExecute(u1,j);
                Log.e("Anirudh", "doInBackground: "+j );
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("Anirudh", "doInBackground: "+res );
            return res;
        }
        @Override
        protected void onPostExecute(String status){

            if(status.equals("ok"))
            {
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                activity_user__home.user=u;
                startActivity(nh);
                finish();
            }

            else{
                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
            }
        }
    }
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}
