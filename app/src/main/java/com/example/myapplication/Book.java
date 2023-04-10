package com.example.myapplication;
import java.util.Date;

public class Book
{
    private String authorFirst;
    private String authorLast;
    private Date publishDate;
    private Format format;
    private Subject subject;
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

    public void setFormat(String formatting)
    {
        for(Format i : Format.values())
        {
            if(i.toString() == formatting)
            {
                format = i;
            }
        }
    }

    public String returnFormat()
    {
        if(subject != null)
        {
            return format.toString();
        }

        return null;
    }

    public void setSubject(String topic)
    {
        for(Subject i : Subject.values())
        {
            if(i.toString() == topic)
            {
                subject = i;
            }
        }
    }

    public String returnSubject()
    {
        if(subject != null)
        {
            return subject.toString();
        }

        return null;
    }
}

enum Format
{
    PAPERBACK, HARDBACK, AUDIO, DIGITAL
}

enum Subject
{
    BIOLOGY, FANTASY, PSYCHOLOGY, HISTORY
}