package com.example.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class WindowSizing
{
    //size of the screen
    static int windowSize;

    //captures size of the screen
    public void setWindowSize(Context mContext)
    {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        windowSize = metrics.widthPixels;
    }

    /*returns 1/16th the size of the screen for back button
    and ActionBar title*/
    public int returnWidgetSize()
    {
        return windowSize/16;
    }

    //returns 1/10th the size of the screen for ActionBar
    public int returnActionBarSize(){return windowSize/10;}

    //returns the size of the screen
    public int returnWindowSize()
    {
        return windowSize;
    }
}

