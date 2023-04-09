package com.example.myapplication;
import java.util.Date;

public class Book
{
    private String authorFirst;
    private String authorLast;
    private Date publishDate;
    //private String format;
    //private String subject;
    private boolean checkedOut;

    public void setAuthor(String firstName, String lastName)
    {
        authorFirst = firstName;
        authorLast = lastName;
    }

    public String returnAuthor()
    {
        return authorFirst + authorLast;
    }

    public void setDate(Date date)
    {
        publishDate = date;
    }

    public Date returnDate()
    {
        return publishDate;
    }

    public void setCheckedOut()
    {
        checkedOut = !checkedOut;
    }

    public boolean returnCheckOut()
    {
        return checkedOut;
    }
}