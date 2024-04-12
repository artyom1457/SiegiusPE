package com.example.siegiuspe.Drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Utils;

public class DrawableBitmap extends DrawableObject {

    protected final Bitmap bitmap;
    protected final int x,y;
    protected final int width,height;

    public DrawableBitmap(Bitmap bitmap,int x, int y, int width,int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
    }

    @Override
    public void drawObject(Canvas canvas)
    {
        Utils.drawBitmap(canvas,bitmap,x,y,width,height);
    }
}
