package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //generate screen display using "activity_main" XML file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the size of the screen
        WindowSizing setWindowSize = new WindowSizing();
        setWindowSize.setWindowSize(this);

        //set toolbar height to 1/10th size of screen and set as Activity's ActionBar
        int actionBarSize = setWindowSize.returnActionBarSize();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getLayoutParams().height = actionBarSize;
        setSupportActionBar(toolbar);
        //set default actionbar title to invisible
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set title height to 1/16th size of screen
        int widgetSize = new WindowSizing().returnWidgetSize();
        TextView title = (TextView)findViewById(R.id.title);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, widgetSize);

        //buttons to move user to option1 or option2 page
        Button option1 = (Button)findViewById(R.id.option1);
        Button option2 = (Button)findViewById(R.id.option2);

        /*set listener so button will move user to author and title search page
        when clicked*/
        option1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent startIntent = new Intent(MainActivity.this, AuthorAndTitleSearch.class);
                //startActivity(startIntent);
            }
        });

       /*set listener so button will move user to subject search page
        when clicked*/
        option2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent startIntent = new Intent(MainActivity.this, SubjectSearch.class);
                //startActivity(startIntent);
            }
        });
    }
}