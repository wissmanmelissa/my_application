package com.example.myapplication;
import java.util.Date;

public class Book
{
    private String[] authorNames;
    private Date publishDate;
    private Format format;
    private Subject subject;
    private boolean checkedOut = false;

    Book(Date date, String format, String subject)
    {
        setDate(date);
        setFormat(format);
        setSubject(subject);
    }

    private void setAuthors(String... names)
    {
        authorNames = new String[names.length];

        for(int i = 0; i < names.length; i++)
        {
            authorNames[i] = names[i];
        }
    }

    public String[] returnAuthor()
    {
        return authorNames;
    }

    private void setDate(Date date)
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

    private void setFormat(String formatting)
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

    private void setSubject(String topic)
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