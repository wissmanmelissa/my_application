package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class DataOrganizer
{
    //list of ALL books available
    Book[] listOfBooks;

    Context mContext;

    /*"mContext" initialized to Context of
    Activity using current "DataOrganizer" Object*/
    DataOrganizer(Context context)
    {
        mContext = context;
    }

    //Creates and returns list of ALL books available using the "books" file
    public Book[] getBooks()
    {
        //Initializes list of book if list is currently null
        if(listOfBooks == null)
        {
            //Create JSONArray using JSON file
            BufferedReader reader = null;
            String currentLine;
            JSONArray jsonArray = null;

            try
            {
                reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("books.json")));
                currentLine = reader.readLine();
                jsonArray = new JSONArray(currentLine);

                /*Contents of "jsonArray" copied to "listOfBooks"*/
                if(jsonArray != null)
                {
                    listOfBooks = new Book[jsonArray.length()];

                    for(int x = 0; x < jsonArray.length(); x++)
                    {
                        JSONObject currentObj = jsonArray.getJSONObject(x);
                        Book currentBook = new Book(currentObj.getString("name"), currentObj.getString("date"), currentObj.getString("format"), currentObj.getString("subject"));

                        /*inner array of author names used to initialize list of author names
                        contained in current "Book" object*/
                        JSONArray authorArray = currentObj.getJSONArray("authors");
                        String[] authors = new String[authorArray.length()];
                        for(int b = 0; b < authorArray.length(); b++)
                        {
                            authors[b] = authorArray.getString(b);
                        }
                        currentBook.setAuthors(authors);

                        listOfBooks[x] = currentBook;
                    }
                }
            }
            catch(JSONException | IOException exception)
            {
                Log.e("ADDING_ENTRIES", exception.toString());
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch(IOException exception)
                {
                    Log.e("File Closure Failure", exception.toString());
                }
            }
        }

        //returns list of ALL books
        return listOfBooks;
    }
}
