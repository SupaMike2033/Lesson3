package com.example.lesson3.Calc;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.lesson3.R;

public class MyAdapter extends ArrayAdapter {

    private final Activity activity;
    private final Integer[] imageID;
    private final Integer[] themeName;


    public MyAdapter(Activity activity, Integer[] imageID, Integer[] themeName) {
        super(activity, R.layout.choose_theme, themeName);
        this.activity = activity;
        this.imageID = imageID;
        this.themeName = themeName;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;
        LayoutInflater inflater = activity.getLayoutInflater();
        rowView = inflater.inflate(R.layout.adapter_list, null, true);

        ImageView imageView = rowView.findViewById(R.id.img);
        TextView textView = rowView.findViewById(R.id.txt);

        imageView.setImageResource(imageID[position]);
        textView.setText(themeName[position]);

        return rowView;
    }
}
