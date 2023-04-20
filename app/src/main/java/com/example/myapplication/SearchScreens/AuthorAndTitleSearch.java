package com.example.myapplication.SearchScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.WindowSizing;

public class AuthorAndTitleSearch extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //generate screen display using "author_and_title" XML file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_and_title);

        //Activity's Context
        Context mContext = this;

        //submission button
        Button submission = (Button) findViewById(R.id.submission);
        //Edittext that catches author name given by user
        EditText author_input = (EditText) findViewById(R.id.author_input);
        //Edittext that catches book title given by user
        EditText title_input = (EditText) findViewById(R.id.title_input);

        //set toolbar height to 1/10th size of screen and set as Activity's ActionBar
        int actionBarSize = new WindowSizing().returnActionBarSize();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getLayoutParams().height = actionBarSize;
        setSupportActionBar(toolbar);
        //set default actionbar title to invisible
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set back button and title height to 1/16th size of screen
        int widgetSize = new WindowSizing().returnWidgetSize();
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.getLayoutParams().height = widgetSize;
        TextView title = (TextView)findViewById(R.id.title);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, widgetSize);

        /*set submit button to capture author and title input (if provided)
        and use them to generate and display a series of book options that
        match user search parameters*/
        submission.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        //set back button to return to previous screen when presses
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
