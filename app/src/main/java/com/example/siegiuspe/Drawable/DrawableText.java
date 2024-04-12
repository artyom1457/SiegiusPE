package com.example.siegiuspe.Drawable;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawableText extends DrawableObject{

    private final String string;
    private final int x;
    private final int y;
    Paint paint;

    public DrawableText(String s, int x, int y, Paint paint)
    {
        string = s;
        this.x = x;
        this.y = y;
        this.paint = paint;
    }


    @Override
    public void drawObject(Canvas canvas) {
        canvas.drawText(string,x,y,paint);
    }
}
