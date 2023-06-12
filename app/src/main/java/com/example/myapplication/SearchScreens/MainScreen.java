package com.example.myapplication.SearchScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.WindowSizing;


public class MainScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //generate screen display using "main_screen" XML file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

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

        /*buttons to move user to author and/or title search OR
        subject pagE*/
        Button authorAndTitle = (Button)findViewById(R.id.authorAndTitle);
        Button subjects = (Button)findViewById(R.id.subjects);

        /*set listener so button will move user to author and title search page
        when clicked*/
        authorAndTitle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(MainScreen.this, AuthorAndTitleSearch.class);
                startActivity(startIntent);
            }
        });

       /*set listener so button will move user to subject search page
        when clicked*/
        subjects.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(MainScreen.this, SubjectSearch.class);
                startActivity(startIntent);
            }
        });
    }
}