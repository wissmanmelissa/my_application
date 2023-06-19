package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.myapplication.DataObjects.Book;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    Book exampleBook1 = Book.createBook("Gone With the Wind", "1936-06-30", "PAPERBACK", "FICTION", new String[]{"Margret Mitchell"});
    Book exampleBook2 = Book.createBook("The Phantom Tollbooth", "1961-09-30", "VIDEO", "SCIENCE FICTION", new String[]{"Norton Juster"});

    @Test
    public void checkBookTitle()
    {
        assertEquals("Gone With the Wind", exampleBook1.returnTitle());
    }

    @Test
    public void checkBookDate()
    {
        assertEquals("1936-06-30", exampleBook1.returnDate());
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
    public void invalidBook()
    {
        assertNull(exampleBook2);
    }
}