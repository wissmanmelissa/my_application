package com.example.myapplication.SearchScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataObjects.Book;
import com.example.myapplication.DataObjects.Book.Subject;
import com.example.myapplication.DataObjects.DataOrganizer;
import com.example.myapplication.R;
import com.example.myapplication.SearchResults.BookRowAdapter;
import com.example.myapplication.WindowSizing;

import java.util.HashMap;
import java.util.List;

public class SubjectSearch extends AppCompatActivity
{
    //chose chosen by user
    String subject_choice;
    public void setSubjectChoice(String subject_chosen)
    {
        subject_choice = subject_chosen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //generate screen display using "target_main" XML file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_search);

        //Activity's Context
        Context mContext = this;

        //sets the columns' size to 1/6 the size of screen
        //sets table's padding to 1/32 the size of screen
        WindowSizing getWindowSize = new WindowSizing();
        int width = getWindowSize.returnWindowSize();
        int columnSize = width/6;

        //submission button
        Button submission = (Button) findViewById(R.id.submission);

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
        TextView appTitle = (TextView)findViewById(R.id.title);
        appTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, widgetSize);

        //Set of possible book subjects used to initialize subject spinner
        String[] subjects;

        /*set of possible subject choices initialized with
        set of possible values for Enum "Subject"*/
        Subject[] setOfSubjects = Subject.values();
        subjects = new String[setOfSubjects.length + 1];

        //first spinner option set to "{Choose an element}"
        subjects[0] = "{Choose an element}";

        for(int i = 0; i < setOfSubjects.length; i++)
        {
            subjects[i + 1] = setOfSubjects[i].toString();
        }

        Spinner subject_spinner = (Spinner) findViewById(R.id.subject_Spinner);
        ArrayAdapter element_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, subjects);
        subject_spinner.setAdapter(element_adapter);

        //subject chosen by user from spinner captured
        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String subject_chosen = parent.getSelectedItem().toString();
                setSubjectChoice(subject_chosen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        /*set submit button to capture subject chosen (if provided) and use it
        to generate and display a series of book options that match user search
        parameters*/
        submission.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //indicates if too many search results may be returned
                boolean tooManyResults = false;

                /*Checks if user chose the "{Choose an element}"
                option from the element spinner. If they did, sets
                "tooManyResults" to true*/
                if (subject_choice.equals("{Choose an element}"))
                {
                    tooManyResults = true;
                }

                /*Displays message "Please provide more specific search parameters" if
                user has given search parameters that would return too many results*/
                if (tooManyResults)
                {
                    Toast.makeText(mContext, "Please provide more specific search parameters", Toast.LENGTH_LONG).show();
                }
                /*if user chosen a specific subject,
                list of all books from that subject are extracted from
                HashMap */
                else
                {
                    DataOrganizer data = new DataOrganizer(mContext);
                    HashMap<String, List<Book>> listOfBooks = data.getSubjectMap();
                    List<Book> listOfOptions = listOfBooks.get(subject_choice);

                    if(listOfOptions != null)
                    {
                        /*Creates search results display using list of all Book objs that
                        match user input and the width of screen*/
                        RecyclerView entriesHolder = (RecyclerView)findViewById(R.id.entriesHolder);
                        entriesHolder.setLayoutManager(new LinearLayoutManager(mContext));
                        BookRowAdapter adapter = new BookRowAdapter(mContext, listOfOptions, width);
                        entriesHolder.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }
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