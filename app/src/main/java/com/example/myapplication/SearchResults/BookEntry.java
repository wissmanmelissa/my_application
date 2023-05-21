package com.example.myapplication.SearchResults;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.DataObjects.Book;
import com.example.myapplication.R;
import com.example.myapplication.SearchScreens.SubjectSearch;

public class BookEntry extends RecyclerView.ViewHolder
{

    private TextView authorNames;
    private TextView title;
    private TextView submitButton;
    private Context mContext;

    //initializes row/EntryHolder Obj
    public BookEntry(ViewGroup container, ViewGroup parent, int width, Context context)
    {
        super(container);

        int columnSize = width/3;

        authorNames = (TextView) itemView.findViewById(R.id.book_author);
        authorNames.getLayoutParams().width = columnSize;

        title = (TextView)(itemView.findViewById(R.id.book_title));
        title.getLayoutParams().width = columnSize;

        submitButton = (TextView)itemView.findViewById(R.id.submit_book);

        mContext = context;
    }

    /*returns TextView that can be clicked to move user to search results page
    AND pass relevant search parameters to search results page to generate
    relevant results*/
    public void createLink(String title, TextView submit_button)
    {
        submit_button.setText("Choose Book");
        submit_button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        submit_button.setTextColor(Color.BLUE);

        submit_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent startIntent = new Intent(mContext, Search_Results.class);
                startIntent.putExtra("title", title);
                mContext.startActivity(startIntent);
            }
        });
    }

    //binds EntryHolder/row to Book Object
    public void bind(Book entry)
    {
        //create string for author column containing ALL authors
        String[] authors = entry.returnAuthor();
        String authorColText = "";
        for(int x = 0; x < authors.length; x++)
        {
            authorColText = authorColText + authors[x] + "\n";
        }

        //set the row's column text and button and add to table
        authorNames.setText(authorColText);

        //String for title column
        String newTitle = "";

        //checks if title is too wide for column
        if(entry.returnTitle().length() > 15)
        {
            //Splits title into different words/by spaces between words
            String[] splitTitle = entry.returnTitle().split(" ");

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
        else
        {
            newTitle = entry.returnTitle();
        }

        title.setText(newTitle);

        //Button dynamically created for each book option
        createLink(entry.returnTitle(), submitButton);
    }
}
