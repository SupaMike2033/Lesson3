package com.example.lesson3.Counters;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lesson3.R;

public class CountersMain extends AppCompatActivity {

    private static final String KEY_COUNTER = "KeyCounter";
    private TextView textCounter1;
    private TextView textCounter2;
    private TextView textCounter3;
    DataBucket dataBucket = new DataBucket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        textCounter1 = findViewById(R.id.text1);
        textCounter2 = findViewById(R.id.text2);
        textCounter3 = findViewById(R.id.text3);

        setButton1ClickListener();
        setButton2ClickListener();
        setButton3ClickListener();
    }

    private void setButton1ClickListener() {
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBucket.incrementCounter1();
                textCounter1.setText(String.format("%d", dataBucket.getCounter1()));
            }
        });
    }

    private void setButton2ClickListener() {
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBucket.incrementCounter2();
                textCounter2.setText(String.format("%d", dataBucket.getCounter2()));
            }
        });
    }

    private void setButton3ClickListener() {
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBucket.incrementCounter3();
                textCounter3.setText(String.format("%d", dataBucket.getCounter3()));
            }
        });
    }

    // это если DataBucket implements Serializable
    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
//        instanceState.putSerializable(KEY_COUNTER, dataBucket);
        instanceState.putParcelable(KEY_COUNTER, dataBucket);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
//        dataBucket = (DataBucket) instanceState.getSerializable(KEY_COUNTER);
        dataBucket = (DataBucket) instanceState.getParcelable(KEY_COUNTER);
        restoreTextFields();
    }

    private void restoreTextFields() {
        textCounter1.setText(String.format("%d", dataBucket.getCounter1()));
        textCounter2.setText(String.format("%d", dataBucket.getCounter2()));
        textCounter3.setText(String.format("%d", dataBucket.getCounter3()));
    }
}
