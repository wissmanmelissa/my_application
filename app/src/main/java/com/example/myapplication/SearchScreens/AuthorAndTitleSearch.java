package com.example.myapplication.SearchScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DataObjects.*;
import com.example.myapplication.R;
import com.example.myapplication.SearchResults.BookRowAdapter;
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

        //set sizing for columns/table based on screen size
        int width = new WindowSizing().returnWindowSize();

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
                    List<Book> listOfBooks = authorBooks.get(author_input.getText().toString().trim().toUpperCase());

                    //if there are some books by chosen author
                    if(listOfBooks != null)
                    {
                        //if user provided a title search parameter
                        if(!title_input.getText().toString().trim().equals(""))
                        {
                            /*add all books to "listOfOptions" that match
                            user search parameters*/
                            for(int x = 0; x < listOfBooks.size(); x++)
                            {
                                if(listOfBooks.get(x).returnTitle().equals(title_input.getText().toString().trim().toUpperCase()))
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
                    List<Book> listOfBooks = data.getBooks();
                    for(int i = 0; i < listOfBooks.size(); i++)
                    {
                        if(listOfBooks.get(i).returnTitle().equals(title_input.getText().toString().trim().toUpperCase()))
                        {
                            listOfOptions.add(listOfBooks.get(i));
                        }
                    }
                }

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
