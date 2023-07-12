package com.example.myapplication.SearchResults;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.DataObjects.Book;
import com.example.myapplication.R;

import java.time.LocalDate;

public class Search_Results extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        //back button that will be used to move to previous page
        ImageView back_button = (ImageView) findViewById(R.id.back_button);

        /*Book extra will be extracted (if present)
        from Intent passed from previous page*/
        Intent intent = getIntent();
        Book entry = null;
        if(intent.hasExtra("book"))
        {
            entry = (Book) intent.getSerializableExtra("book");
        }

        /*Create text for "author" column using list of authors
        extracted from chosen Book object*/
        TextView book_author = (TextView) findViewById(R.id.book_author);
        String[] authors = entry.returnAuthors();
        String author_text = "";
        for(int i = 0; i < authors.length; i++)
        {
            author_text += authors[i] + "\n";
        }
        book_author.setText(author_text);

        /*set "title", "format", "publish date", and "subject"
        columns using data extracted from chosen Book Object*/
        TextView book_title = (TextView) findViewById(R.id.book_title);
        book_title.setText(entry.returnTitle());
        TextView book_pub_date = (TextView) findViewById(R.id.book_pub_date);

        LocalDate bookDate = entry.returnDate();
        String bookDateText = bookDate.getYear() + "-" + bookDate.getMonthValue() + "-" + bookDate.getDayOfMonth();


        book_pub_date.setText(bookDateText);
        TextView book_format = (TextView) findViewById(R.id.book_format);
        book_format.setText(entry.returnFormat());
        TextView book_subject = (TextView) findViewById(R.id.book_subject);
        book_subject.setText(entry.returnSubject());

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