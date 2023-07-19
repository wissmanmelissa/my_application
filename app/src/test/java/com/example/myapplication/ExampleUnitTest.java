package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.myapplication.DataObjects.Book;

import java.time.LocalDate;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    Book exampleBook1 = Book.createBook("Gone With the Wind", "1936" , "06", "30", "PAPERBACK", "FICTION", new String[]{"Margret Mitchell"});

    @Test
    public void checkBookDate()
    {

        assertEquals(LocalDate.of(1936, 06, 30), exampleBook1.returnDate());
    }

    @Test
    public void checkBookFormat()
    {
        assertEquals("PAPERBACK", exampleBook1.returnFormat());
    }

    @Test
    public void checkBookSubject()
    {
        assertEquals("FICTION", exampleBook1.returnSubject());
    }

    @Test
    public void invalidFormat()
    {
        Book exampleBook2 = Book.createBook("The Phantom Tollbooth", "1961", "09", "30", "VIDEO", "FICTION", new String[]{"Norton Juster"});
        assertNull(exampleBook2);
    }

    @Test
    public void invalidSubject()
    {
        Book exampleBook2 = Book.createBook("The Phantom Tollbooth", "1961", "09","30", "AUDIO", "SCIENCE FICTION", new String[]{"Norton Juster"});
        assertNull(exampleBook2);
    }

    @Test
    public void invalidAuthor()
    {
        Book exampleBook2 = Book.createBook("The Phantom Tollbooth", "1961" , "09", "30", "AUDIO", "FICTION", new String[]{"Norton Juster", "Frank"});
        assertNull(exampleBook2);
    }

    @Test
    public void pastCurrentDate()
    {
        LocalDate currentDate = LocalDate.now();
        int exampleYear = currentDate.getYear() + 5;

        Book exampleBook2 = Book.createBook("The Phantom Tollbooth", String.valueOf(exampleYear), "09", "30", "AUDIO", "FICTION", new String[]{"Norton Juster"});
        assertNull(exampleBook2);
    }

    @Test
    public void invalidDate()
    {
        Book exampleBook2 = Book.createBook("The Phantom Tollbooth", "1961" , "09", "75", "AUDIO", "FICTION", new String[]{"Norton Juster"});
        assertNull(exampleBook2);
    }
}