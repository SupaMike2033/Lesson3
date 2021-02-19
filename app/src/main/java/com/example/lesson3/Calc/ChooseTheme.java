package com.example.lesson3.Calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lesson3.R;

public class ChooseTheme extends AppCompatActivity {

    ListView list;
    Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_theme);

        constants = new Constants();

        MyAdapter adapter = new MyAdapter(ChooseTheme.this, constants.images, constants.themeNames);    // дописать MyAdapter!!!
        list = findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(Constants.CURRENT_THEME, position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
