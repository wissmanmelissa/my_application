package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.DataObjects.DataOrganizer;
import com.example.myapplication.SearchScreens.MainScreen;

public class StartScreen extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        /*Activity that generates an Splash
        page while the application initializes
        the local storage and captures the screen
        size*/

        //Activity's Context
        Context mContext = this;

        //generate screen display using "start screen" XML file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        //set toolbar height to 1/10th size of screen and set as Activity's ActionBar
        int actionBarSize = new WindowSizing().returnActionBarSize();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getLayoutParams().height = actionBarSize;
        setSupportActionBar(toolbar);
        //set default actionbar title to invisible
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set title height to 1/16th size of screen
        int widgetSize = new WindowSizing().returnWidgetSize();
        TextView title = (TextView)findViewById(R.id.title);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, widgetSize);


        //thread used to create all local storage of data
        Thread welcomeThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    super.run();
                    DataOrganizer data = new DataOrganizer(mContext);
                    data.getAuthorMap();
                    data.getBooks();
                    data.getSubjectMap();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    /*once local storage of data created, user moved to main screen*/
                    Intent i = new Intent(StartScreen.this, MainScreen.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        //start thread
        welcomeThread.start();
    }
}