package com.example.myapplication.DataObjects;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.DataObjects.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.myapplication.DataObjects.Book;

public class DataOrganizer
{
    //list of ALL books available
    Book[] listOfBooks;

    //Map that maps author name to list of associated books
    HashMap<String, List<Book>> bookAuthorMap;

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

    //returns Map that maps author name to list of associated books
    public HashMap<String, List<Book>> getAuthorMap()
    {
        //if hashmap hasn't yet been created, intialize hashmap
        if(bookAuthorMap == null)
        {
            bookAuthorMap = new HashMap<>();

            //if list of books hasn't been created, initialize list of books
            if(listOfBooks == null)
            {
                getBooks();
            }


            for(int i = 0; i < listOfBooks.length; i++)
            {
                /*transverse list of authors for each book and map author names
                to ALL books associated with author*/
                for(int x = 0; x < listOfBooks[i].returnAuthor().length; x++)
                {
                    /*if author name already mapped to one or more books,
                    simply add new book to already existing list*/
                    if(bookAuthorMap.containsKey(listOfBooks[i].returnAuthor()[x]))
                    {
                        List<Book> currentList = bookAuthorMap.get(listOfBooks[i].returnAuthor()[x]);
                        currentList.add(listOfBooks[i]);
                        bookAuthorMap.replace(listOfBooks[i].returnAuthor()[x], currentList);
                    }
                    /*if author name ISN'T mapped to ANY books,
                    create new list of books and map to author name*/
                    else
                    {
                        List<Book> currentList = new ArrayList<>();
                        currentList.add(listOfBooks[i]);
                        bookAuthorMap.put(listOfBooks[i].returnAuthor()[x], currentList);
                    }
                }

            }
        }

        return bookAuthorMap;
    }
}
