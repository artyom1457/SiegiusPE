package com.example.siegiuspe.Managers;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public class SystemUiManager {

    private View mDecorView;
    private final Handler mHideSystemUiHandler;
    public static final int INITIAL_HIDE_DELAY = 300;


    public SystemUiManager(View DecorView)
    {
        this.mDecorView = DecorView;

        mHideSystemUiHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if(msg.what == 0)
                {
                    hideSystemUIALL();
                }
                else if(msg.what == 1)
                {
                    hideSystemUINavigation();
                }
                return true;
            }
        });

    }

    public void changeDecorView(View decorView)
    {
        this.mDecorView = decorView;
    }


    public void hideSystemUINavigation() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.

    public void hideSystemUIALL() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.

    public void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void delayedHideNavigation(int delayMillis)
    {
        mHideSystemUiHandler.removeMessages(1);
        mHideSystemUiHandler.sendEmptyMessageDelayed(1,delayMillis);
    }
    public void delayedHideALL(int delayMillis)
    {
        mHideSystemUiHandler.removeMessages(0);
        mHideSystemUiHandler.sendEmptyMessageDelayed(0,delayMillis);
    }

    public void removeMessages(int what)
    {
        mHideSystemUiHandler.removeMessages(what);
    }
}
