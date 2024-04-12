package com.example.siegiuspe;

import android.content.Context;

import com.example.siegiuspe.GameObjects.BaseObject;

/** Contains global (but typically constant) parameters about the current operating context */
public class ContextParameters {
    public static int viewWidth;
    public static int viewHeight;
    public static int gameWidth;
    public static int gameHeight;

    public static int bgX = -555;
    public static int bgY= 0;
    public Context context;


    
    public ContextParameters() {
        super();
    }
}
