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


    //Book data variables
    private String[] authorNames;
    private String title;
    private LocalDate publishDate;
    private Format format;
    private Subject subject;

    //returns new book object if all book data is valid/accurate
    public static Book createBook(String title, String pubYear, String pubMonth, String pubDay, String format, String subject, String[] names)
    {
        Book newBook = new Book();

        //set book data and return true if valid book data is returned
        boolean properFormat = newBook.setFormat(format);
        boolean properSubject = newBook.setSubject(subject);
        boolean properAuthors = newBook.setAuthors(names);
        boolean properDate = newBook.setDate(pubYear, pubMonth, pubDay);

        //"null" Book object returned if any book data is invalid
        if(!properFormat || !properSubject || !properAuthors || !properDate)
        {
            return null;
        }

        newBook.setTitle(title);

        return newBook;
    }

    //sets list of book authors and returns true if valid data passed to function
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

            //returns false if current author name doesn't
            //contain at least a first and last name
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

    //sets date variable if valid month, day and year value is passed
    //to function, otherwise false is returned
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

    //sets format and returns true if valid format passed to function
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

    //sets subject and returns true if valid subject passed to function
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