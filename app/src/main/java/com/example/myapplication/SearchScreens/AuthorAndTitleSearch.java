package com.example.myapplication.SearchScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.DataObjects.*;
import com.example.myapplication.R;
import com.example.myapplication.WindowSizing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        //table of book options and table column labels
        LinearLayout optionsTable = (LinearLayout) findViewById(R.id.table);
        TextView author_label = (TextView) findViewById(R.id.author_label);
        TextView title_label = (TextView) findViewById(R.id.title_label);

        //set sizing for columns/table based on screen size
        int width = new WindowSizing().returnWindowSize();
        int columnSize = width/6;
        int paddingSize = width/32;
        optionsTable.setPadding(paddingSize, 0, 0, 0);
        author_label.getLayoutParams().width = columnSize;
        title_label.getLayoutParams().width = columnSize;

        //sets table to invisible
        optionsTable.setVisibility(View.INVISIBLE);

        /*set submit button to capture author and title input (if provided)
        and use them to generate and display a series of book options that
        match user search parameters*/
        submission.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataOrganizer data = new DataOrganizer(mContext);

                //list of all books that matches search parameters
                List<Book> listOfOptions = new ArrayList<>();

                //if user provided author search parameter
                if(!author_input.getText().toString().trim().equals(""))
                {
                    //obtain list of all books that matches author search parameter
                    HashMap<String, List<Book>> authorBooks = data.getAuthorMap();
                    List<Book> listOfBooks = authorBooks.get(author_input.getText().toString().trim());

                    //if there are some books by chosen author
                    if(listOfBooks != null)
                    {
                        //if user provided a title search parameter
                        if(!title_input.getText().toString().trim().equals(""))
                        {
                            /*add all books to "listOfOptions" that match
                            user search parameters
                             */
                            for(int x = 0; x < listOfBooks.size(); x++)
                            {
                                if(listOfBooks.get(x).returnTitle().equals(title_input.getText().toString().trim()))
                                {
                                    listOfOptions.add(listOfBooks.get(x));
                                }
                            }
                        }
                        //if user provided only author search parameter
                        else
                        {
                            listOfOptions = listOfBooks;
                        }
                    }
                }
                //if user provided title search parameter
                else if(!title_input.getText().toString().trim().equals(""))
                {
                    /*add all books to "listOfOptions" that match user
                    search parameters*/
                    Book[] listOfBooks = data.getBooks();
                    for(int i = 0; i < listOfBooks.length; i++)
                    {
                        if(listOfBooks[i].returnTitle().equals(title_input.getText().toString().trim()))
                        {
                            listOfOptions.add(listOfBooks[i]);
                        }
                    }
                }

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

                        //create string for author column containing ALL authors
                        String[] authors = listOfOptions.get(i).returnAuthor();
                        String authorColText = "";
                        for(int x = 0; x < authors.length; x++)
                        {
                            authorColText = authors[x] + "\n";
                        }

                        //set the row's column text and add to table
                        author_column.setText(authorColText);
                        title_column.setText(listOfOptions.get(i).returnTitle());
                        entryRow.addView(author_column);
                        entryRow.addView(title_column);
                        optionsTable.addView(entryRow);
                    }
                }
                //show error message if no valid search parameters
                else
                {
                    Toast.makeText(mContext, "Please provide different search parameters", Toast.LENGTH_LONG).show();
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
