package com.example.myapplication.DataObjects;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.DataObjects.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.example.myapplication.DataObjects.Book;

public class DataOrganizer
{
    //list of ALL books available
    static List<Book> listOfBooks;

    //Map that maps author name to list of associated books
    static HashMap<String, List<Book>> bookAuthorMap;
    //Map that maps each subject to list of associated books
    static HashMap<String, List<Book>> bookSubjectMap;


    Context mContext;

    /*"mContext" initialized to Context of
    Activity using current "DataOrganizer" Object*/
    public DataOrganizer(Context context)
    {
        mContext = context;
    }

    //Creates and returns list of ALL books available using the "books" file
    public List<Book> getBooks()
    {
        //Initializes list of book if list is currently null
        if(listOfBooks == null)
        {
            //Create JSONArray using "books.json" file
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
                    listOfBooks = new ArrayList<>();

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

                        listOfBooks.add(currentBook);
                    }
                }
            }
            catch(JSONException | IOException exception)
            {
                exception.printStackTrace();
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

            Collections.sort(listOfBooks);
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


            for(int i = 0; i < listOfBooks.size(); i++)
            {
                /*transverse list of authors for each book and map author names
                to ALL books associated with author*/
                for(int x = 0; x < listOfBooks.get(i).returnAuthors().length; x++)
                {
                    /*if author name already mapped to one or more books,
                    simply add new book to already existing list*/
                    if(bookAuthorMap.containsKey(listOfBooks.get(i).returnAuthors()[x]))
                    {
                        List<Book> currentList = bookAuthorMap.get(listOfBooks.get(i).returnAuthors()[x]);
                        currentList.add(listOfBooks.get(i));
                        bookAuthorMap.replace(listOfBooks.get(i).returnAuthors()[x], currentList);
                    }
                    /*if author name ISN'T mapped to ANY books,
                    create new list of books and map to author name*/
                    else
                    {
                        List<Book> currentList = new ArrayList<>();
                        currentList.add(listOfBooks.get(i));
                        bookAuthorMap.put(listOfBooks.get(i).returnAuthors()[x], currentList);
                    }
                }

            }
        }

        return bookAuthorMap;
    }

    //returns Map that maps each subject to list of associated books
    public HashMap<String, List<Book>> getSubjectMap()
    {
        //if hashmap hasn't yet been created, intialize hashmap
        if(bookSubjectMap == null)
        {
            bookSubjectMap = new HashMap<>();

            //if list of books hasn't been created, initialize list of books
            if(listOfBooks == null)
            {
                getBooks();
            }


            for(int i = 0; i < listOfBooks.size(); i++)
            {
                    /*if subject already mapped to one or more books,
                    simply add new book to already existing list*/
                    if(bookSubjectMap.containsKey(listOfBooks.get(i).returnSubject()))
                    {
                        List<Book> currentList = bookSubjectMap.get(listOfBooks.get(i).returnSubject());
                        currentList.add(listOfBooks.get(i));
                        bookSubjectMap.replace(listOfBooks.get(i).returnSubject(), currentList);
                    }
                    /*if subject ISN'T mapped to ANY books,
                    create new list of books and map to subject*/
                    else
                    {
                        List<Book> currentList = new ArrayList<>();
                        currentList.add(listOfBooks.get(i));
                        bookSubjectMap.put(listOfBooks.get(i).returnSubject(), currentList);
                    }

            }
        }

        return bookSubjectMap;
    }
}
