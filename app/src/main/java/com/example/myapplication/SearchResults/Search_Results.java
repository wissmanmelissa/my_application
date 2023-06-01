package com.example.myapplication.SearchResults;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.DataObjects.Book;
import com.example.myapplication.DataObjects.DataOrganizer;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.List;

public class Search_Results extends AppCompatActivity
{

    //Context of the current Activity
   Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        //back button that will be used to move to previous page
        ImageView back_button = (ImageView) findViewById(R.id.back_button);

        /*Title and author extras with be extracted (if present)
        from Intent passed from previous page*/
        Intent intent = getIntent();
        String author = null;
        String title = null;
        if(intent.hasExtra("author") && !intent.getStringExtra("author").equals(""))
        {
            author = intent.getStringExtra("author");
        }
        if(intent.hasExtra("title") && !intent.getStringExtra("title").equals(""))
        {
            title = intent.getStringExtra("title");
        }

        DataOrganizer data = new DataOrganizer(mContext);
        List<Book> listOfBooks;
        Book choice = null;

        /*If author search parameter given, extract hashMap
        mapped to author name*/
        if(author != null)
        {
            HashMap<String, List<Book>> authorBooks = data.getAuthorMap();
            listOfBooks = authorBooks.get(author);;

            /*find book matching chosen book title*/
            for (int x = 0; x < listOfBooks.size(); x++)
            {
                if (listOfBooks.get(x).returnTitle().equals(title))
                {
                    choice = listOfBooks.get(x);
                }
            }
        }
        /*If only book title specified, extract chosen book title
        from list of all books*/
        else
        {
            listOfBooks = data.getBooks();
            for(int x = 0; x < listOfBooks.size(); x++)
            {
                if(listOfBooks.get(x).returnTitle().equals(title))
                {
                    choice = listOfBooks.get(x);
                }
            }
        }

        /*Create text for "author" column using list of authors
        extracted from chosen Book object*/
        TextView book_author = (TextView) findViewById(R.id.book_author);
        String[] authors = choice.returnAuthors();
        String author_text = "";
        for(int i = 0; i < authors.length; i++)
        {
            author_text += authors[i] + "\n";
        }
        book_author.setText(author_text);

        /*set "title", "format", "publish date", and "subject"
        columns using data extracted from chosen Book Object*/
        TextView book_title = (TextView) findViewById(R.id.book_title);
        book_title.setText(choice.returnTitle());
        TextView book_pub_date = (TextView) findViewById(R.id.book_pub_date);
        book_pub_date.setText(choice.returnDate());
        TextView book_format = (TextView) findViewById(R.id.book_format);
        book_format.setText(choice.returnFormat());
        TextView book_subject = (TextView) findViewById(R.id.book_subject);
        book_subject.setText(choice.returnSubject());

        //set back button to return to previous screen when presses
        back_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}