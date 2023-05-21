package com.example.myapplication.SearchResults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.DataObjects.Book;
import com.example.myapplication.R;

import java.util.List;

public class BookRowAdapter extends RecyclerView.Adapter<BookEntry>
{

    //Context of Activity initializing current BookRowAdapter Object
    Context mContext;
    //List of Book Objs that match user input
    List<Book> listOfBooks;
    //width of screen
    int width;

    public BookRowAdapter(Context context, List<Book> listOfBooks, int width)
    {
        mContext = context;
        this.listOfBooks = listOfBooks;
        this.width = width;
    }


    @Override
    public BookEntry onCreateViewHolder(ViewGroup parent, int ViewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup)inflater.inflate(R.layout.book_entry, null);

        return new BookEntry(layout, parent, width, mContext);
    }

    //binds each row/EntryHolder to its corresponding book object in the "listOfBooks"
    @Override
    public void onBindViewHolder(BookEntry holder, int position)
    {
        holder.bind(listOfBooks.get(position));
    }

    //gets number of rows needed based on size of "listOfGammas" list
    @Override
    public int getItemCount()
    {
        return listOfBooks.size();
    }
}
