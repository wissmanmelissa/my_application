package com.example.myapplication.DataObjects;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
    private LocalDate publishDate;
    private Format format;
    private Subject subject;

    public static Book createBook(String title, String pubYear, String pubMonth, String pubDay, String format, String subject, String[] names)
    {
        Book newBook = new Book();
        boolean properFormat = newBook.setFormat(format);
        boolean properSubject = newBook.setSubject(subject);
        boolean properAuthors = newBook.setAuthors(names);
        boolean properDate = newBook.setDate(pubYear, pubMonth, pubDay);

        if(!properFormat || !properSubject || !properAuthors || !properDate)
        {
            return null;
        }

        newBook.setAuthors(names);
        newBook.setTitle(title);

        return newBook;
    }

    private boolean setAuthors(String[] names)
    {
        if(names.length == 0)
        {
            return false;
        }

        authorNames = new String[names.length];

        for(int i = 0; i < names.length; i++)
        {
            String[] currentAuthor = names[i].split(" ");

            if(currentAuthor.length < 2)
            {
                return false;
            }

            authorNames[i] = names[i];
        }

        return true;
    }

    public String[] returnAuthors()
    {
        return authorNames;
    }

    private boolean setDate(String year, String month, String day)
    {
        try
        {
            int pubYear = Integer.parseInt(year);
            int pubMonth = Integer.parseInt(month);
            int pubDay = Integer.parseInt(day);

            LocalDate currentDate = LocalDate.now();
            LocalDate bookDate = LocalDate.of(pubYear, pubMonth, pubDay);

            if(currentDate.compareTo(bookDate) > 0)
            {
                publishDate = bookDate;
                return true;
            }
        }
        catch(NumberFormatException | DateTimeParseException e)
        {
            return false;
        }

        return false;
    }

    public LocalDate returnDate()
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