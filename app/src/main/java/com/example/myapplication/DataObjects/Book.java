package com.example.myapplication.DataObjects;

public class Book
{
    //set of possible values for book format
    public enum Format
    {
        PAPERBACK, HARDBACK, AUDIO, DIGITAL
    }

    //set of possible value for book subject
    public enum Subject
    {
        BIOLOGY, PSYCHOLOGY, HISTORY, FICTION
    }


    private String[] authorNames;
    private String title;
    private String publishDate;
    private Format format;
    private Subject subject;
    private boolean checkedOut = false;

    public Book(String title, String date, String format, String subject)
    {
        setDate(date);
        setFormat(format);
        setSubject(subject);
        setTitle(title);
    }

    public void setAuthors(String[] names)
    {
        authorNames = new String[names.length];

        for(int i = 0; i < names.length; i++)
        {
            authorNames[i] = names[i];
        }
    }

    public String[] returnAuthors()
    {
        return authorNames;
    }

    private void setDate(String date)
    {
        publishDate = date;
    }

    public String returnDate()
    {
        return publishDate;
    }

    private void setTitle(String id)
    {
        title = id;
    }

    public String returnTitle()
    {
        return title;
    }

    private void setFormat(String formatting)
    {
        for(Format i : Format.values())
        {
            if(i.name().equals(formatting))
            {
                format = i;
                break;
            }
        }
    }

    public String returnFormat()
    {
        if(format != null)
        {
            return format.toString();
        }

        return null;
    }

    private void setSubject(String topic)
    {
        for(Subject i : Subject.values())
        {
            if(i.name().equals(topic))
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