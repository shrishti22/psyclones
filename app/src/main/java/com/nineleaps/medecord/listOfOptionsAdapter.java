package com.nineleaps.medecord;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class listOfOptionsAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    ProgressDialog pDialog;
    public listOfOptionsAdapter(Context context, String[] values) {
        super(context, R.layout.activity_list_options, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);
//		Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.my_image);
//		imageView.setImageBitmap(bImage);

        // Change icon based on name
        //String s = values[position];

        //System.out.println(s);

//		if (s.equals("WindowsMobile")) {
//			imageView.setImageResource(R.drawable.windowsmobile_logo);
//		} else if (s.equals("iOS")) {
//			imageView.setImageResource(R.drawable.ios_logo);
//		} else if (s.equals("Blackberry")) {
//			imageView.setImageResource(R.drawable.blackberry_logo);
//		} else {
//			imageView.setImageResource(R.drawable.android_logo);
//		}

        return rowView;
    }
}
