package com.nineleaps.medecord;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class list_options extends ListActivity {
    public static String names[]=null;
    public static String dir="",date="";

    public static String pid,emp="";

    public String curr_folder="",curr_class="";
    Intent next,n1,n2;
    public static String data="",user="",fname="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setListAdapter(new listOfOptionsAdapter(this, names));
        next=new Intent(this,activity_display__record.class);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);



        String ss= Integer.toString(position);
        Toast.makeText(this, selectedValue+"---"+ss, Toast.LENGTH_SHORT).show();

        date=selectedValue;
        new get_date_data().execute();


    }

    private class get_date_data extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            //  jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url + "get_date_data");

                JSONObject jsn = new JSONObject();
                jsn.put("did", user);
                jsn.put("pid", pid);
                jsn.put("date", date);
                response = HttpClientConnection.HttpExecute(url, jsn);
                Log.e("Anirudh", "response: "+response );
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getApplicationContext(), "s="+s, Toast.LENGTH_SHORT).show();
            Log.e("Anirudh", "getdata: "+s );
            if(s.endsWith("null"))
            {

                s=s.substring(0,s.length()-4);
            }

            if(s.equalsIgnoreCase("No Data Available"))
            {
                Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_SHORT).show();

            }
            else{

                String d[]=s.split("##");
                Toast.makeText(getApplicationContext(), ""+d.length, Toast.LENGTH_SHORT).show();
                activity_display__record.d=d;
                startActivity(next);

            }

        }
    }


}
