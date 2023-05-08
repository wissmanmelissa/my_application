package com.example.myapplication.SearchScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataObjects.Book;
import com.example.myapplication.DataObjects.Book.Subject;
import com.example.myapplication.DataObjects.DataOrganizer;
import com.example.myapplication.R;
import com.example.myapplication.SearchResults.Search_Results;
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

    /*returns button that will move user to search results page AND
    pass relevant search parameters to search results page to generate
    relevant results*/
    public Button createButton(String title)
    {
        Button bookOption = new Button(this);
        bookOption.setText("Choose Book");

        bookOption.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent startIntent = new Intent(SubjectSearch.this, Search_Results.class);
                startIntent.putExtra("title", title);
                startActivity(startIntent);
            }
        });

        return bookOption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //generate screen display using "target_main" XML file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_search);

        //Activity's Context
        Context mContext = this;

        //table of book options and the labels for all columns within table
        LinearLayout optionsTable = (LinearLayout) findViewById(R.id.table);
        TextView author = (TextView) findViewById(R.id.author_label);
        TextView title = (TextView) findViewById(R.id.title_label);
        TextView options = (TextView) findViewById(R.id.OptionLabel);


        //sets the columns' size to 1/6 the size of screen
        //sets table's padding to 1/32 the size of screen
        WindowSizing getWindowSize = new WindowSizing();
        int width = getWindowSize.returnWindowSize();
        int columnSize = width/6;
        int paddingSize = width/32;
        optionsTable.setPadding(paddingSize, 0, 0, 0);
        author.getLayoutParams().width = columnSize;
        title.getLayoutParams().width = columnSize;
        options.getLayoutParams().width = columnSize;
        //sets table to invisible
        optionsTable.setVisibility(View.INVISIBLE);

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
                        //make table of book options visible
                        optionsTable.setVisibility(View.VISIBLE);
                        //remove all rows added during previous search
                        optionsTable.removeViews(1, optionsTable.getChildCount() - 1);


                        for(int i = 0; i < listOfOptions.size(); i++)
                        {
                            //create new row for current book entry
                            TableRow entryRow = new TableRow(mContext);
                            TextView author_column = new TextView(mContext);
                            TextView title_column = new TextView(mContext);
                            //Button dynamically created for each book option
                            Button optionButton = createButton(listOfOptions.get(i).returnTitle());

                            //create string for author column containing ALL authors
                            String[] authors = listOfOptions.get(i).returnAuthor();
                            String authorColText = "";
                            for(int x = 0; x < authors.length; x++)
                            {
                                authorColText = authorColText + authors[x] + "\n";
                            }

                            //set the row's column text and button and add to table
                            author_column.setText(authorColText);

                            //String for title column
                            String newTitle = "";

                            //checks if title is too wide for column
                            if(listOfOptions.get(i).returnTitle().length() > 15)
                            {
                                //Splits title into different words/by spaces between words
                                String[] splitTitle = listOfOptions.get(i).returnTitle().split(" ");

                                /*adds each word in the title one-by-one to the String for
                                title column, and adds newline character for every 15
                                characters (or as close as possible) to ensure title
                                text isn't too wide for column*/
                                int splitIndex = 15;
                                for(int a = 0; a < splitTitle.length; a++)
                                {
                                    if(newTitle.length() >= splitIndex)
                                    {
                                        newTitle = newTitle + "\n" + splitTitle[a];
                                        splitIndex += 16;
                                    }
                                    else
                                    {
                                        newTitle = newTitle + " " + splitTitle[a];
                                    }
                                }
                            }
                            title_column.setText(newTitle);

                            entryRow.addView(author_column);
                            entryRow.addView(title_column);
                            optionButton.setText("Choose Book");
                            entryRow.addView(optionButton);
                            optionsTable.addView(entryRow);

                            //set padding and size of columns to fit content
                            author_column.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            author_column.setPadding(0, 0, 20, 0);
                            title_column.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            title_column.setPadding(0, 0, 20, 0);
                        }
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