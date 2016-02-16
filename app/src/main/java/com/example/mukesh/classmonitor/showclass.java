package com.example.mukesh.classmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class showclass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_showclass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String course_title = getIntent().getStringExtra("title");
        String Date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        final String url = getIntent().getStringExtra("URL");
        TextView txt = (TextView)findViewById(R.id.textView2);
        txt.append(course_title);
        TextView txt1 = (TextView)findViewById(R.id.textView3);
        txt1.append(time);
        TextView txt2 = (TextView)findViewById(R.id.textView4);
        txt2.append(Date +'\n' + getIntent().getStringExtra("mode"));
        final Button takeme = (Button) findViewById(R.id.button2);
        takeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click


                // moving to showmap activity to select class location .
                Intent activityChangeIntent = new Intent(getApplicationContext(), Drawpath.class);
                activityChangeIntent.putExtra("URL",url);
                startActivityForResult(activityChangeIntent, 100);
            }
        });

    }

}
