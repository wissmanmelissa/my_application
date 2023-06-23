package com.example.myapplication.DataObjects;

import android.util.Log;

import java.io.Serializable;

public class Book implements Comparable<Book>, Serializable
{
    @Override
    public int compareTo(Book otherBook)
    {
        return this.returnTitle().compareTo(otherBook.returnTitle());
    }

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

    public static Book createBook(String title, String date, String format, String subject, String[] names)
    {
        Book newBook = new Book();
        boolean properFormat = newBook.setFormat(format);
        boolean properSubject = newBook.setSubject(subject);

        if(!properFormat || !properSubject)
        {
            return null;
        }

        newBook.setAuthors(names);
        newBook.setDate(date);
        newBook.setTitle(title);

        return newBook;
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

    private boolean setFormat(String formatting)
    {
        String formattingType = formatting.toUpperCase();

        for(Format i : Format.values())
        {
            if(i.name().equals(formattingType))
            {
                format = i;
                return true;
            }
        }

        return false;
    }

    public String returnFormat()
    {
        if(format != null)
        {
            return format.toString();
        }

        return null;
    }

    private boolean setSubject(String topic)
    {
        String subjectType = topic.toUpperCase();

        for(Subject i : Subject.values())
        {
            if(i.name().equals(subjectType))
            {
                subject = i;
                return true;
            }
        }

        return false;
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