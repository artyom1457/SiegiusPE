package com.example.siegiuspe.Drawable;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawableRect extends DrawableObject {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    Paint paint;

    public DrawableRect(int x, int y,int width,int height ,Paint paint)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.paint = paint;
    }

    @Override
    public void drawObject(Canvas canvas) {
        canvas.drawRect(x,y,x+width,y+height,paint);
    }
}
