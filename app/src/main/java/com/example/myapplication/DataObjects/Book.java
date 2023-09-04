package com.example.myapplication.DataObjects;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * "Book" class represents the data for a book stored in the system,
   including author names, publishing date, title, format and subject
 * @author Melissa Wissman
 */

public class Book implements Comparable<Book>, Serializable
{
    @Override
    public int compareTo(Book otherBook)
    {
        return this.returnTitle().compareTo(otherBook.returnTitle());
    }

    /**Default constructor*/
    Book()
    {

    }
    /**
     * Enum class that represents the all the possible Book formats the user may choose
     * @author Melissa Wissman
     */
    public enum Format
    {

        /**
         * Represents the paperback format
         */
        PAPERBACK,
        /**
         * Represents the hardback format
         */
        HARDBACK,
        /**
         * Represents the audio format
         */
        AUDIO,
        /**
         * Represents the digital format
         */
        DIGITAL,
    }

    /**
     * Enum class that represents the all the possible Book subjects the user may choose
     * @author Melissa Wissman
     */
    public enum Subject
    {
        BIOLOGY, PSYCHOLOGY, HISTORY, FICTION
    }


    /**
     * Array of Book's author names*/
    private String[] authorNames;
    /**
     * Book title*/
    private String title;
    /**
     * Book's published date, represented as LocalDate Object*/
    private LocalDate publishDate;
    /**
     * Book's format, represented as Enum constant from Book.Format enum class*/
    private Format format;
    /**
     * Book's subject, represented as Enum constant from Book.Subject enum class*/
    private Subject subject;

    /**
     * Creates a new Book object using data passed IF the data is valid
     * @return new Book Object
     * @param pubYear a String representing the year the book was published, must be valid year
     * @param pubMonth a String representing the month the book was published, must be valid month
     * @param pubDay a String representing the day the book was published, must be valid day
     * @param title a String representing the book title
     * @param format a String representing the book format, must be valid format from enum Book.Format
     * @param subject a String representing the book subject, must be valid subject from enum Book.Subject
     * @param names a String array representing the book authors, all author names must include a first and last name
     */
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

    /**
     * Sets IF all names include a valid first and last name
     * @return a boolean if all author names given were valid
     * @param names String array containing the full name of all authors for the book
     */
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

            authorNames[i] = names[i].toUpperCase();
        }

        return true;
    }

    /**
     * returns the list of authors
     * @return String array of all author names
     */
    public String[] returnAuthors()
    {
        return authorNames;
    }

    /**
     * Sets the published date IF a valid year, month, and day have been given
     * @return a boolean if the year, day, and month are provided
     * @param year String representing the published year
     * @param day String representing the published day
     * @param month String representing the published month
     */
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
        catch(NumberFormatException | DateTimeException e)
        {
            return false;
        }

        return false;
    }

    /**
     * returns the published date
     * @return a LocalDate object representing the published date
     */
    public LocalDate returnDate()
    {
        return publishDate;
    }

    /**
     * Sets argument to all caps and uses it to set title
     * @param id String representing title
     */
    private void setTitle(String id)
    {
        title = id.toUpperCase();
    }

    /**
     * returns the title
     * @return String representing the title
     */
    public String returnTitle()
    {
        return title;
    }

    /**
     * Sets the subject IF the format is a valid value of enum Book.Format
     * @return a true if subject given was valid
     * @param formatting String representing the format
     */
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

    /**
     * returns the format
     * @return String representing the format
     */
    public String returnFormat()
    {
        if(format != null)
        {
            return format.toString();
        }

        return null;
    }

    /**
     * Sets the subject IF the subject is a valid value of enum Book.Subject
     * @return a true if subject given was valid
     * @param topic String representing the subject
     */
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

    /**
     * returns the subject
     * @return String representing the subject
     */
    public String returnSubject()
    {
        if(subject != null)
        {
            return subject.toString();
        }

        return null;
    }
}