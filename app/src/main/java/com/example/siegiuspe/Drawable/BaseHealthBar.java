package com.example.siegiuspe.Drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.siegiuspe.ContextParameters;

public class BaseHealthBar extends DrawableObject
{

    private final Paint hitPoints = new Paint();
    private final Paint lostHitPoints = new Paint();

    private Rect hitPointsRect;
    private Rect lostHitPointsRect;

    private int width;

    private int green = 255;
    private int red = 0;

    private boolean player;

    private int maxHp;
    private int hp;

    private int left;
    private int middle;
    private int right;
    private int top;
    private int bottom;


    public BaseHealthBar(boolean player)
    {
        maxHp = 5000;
        hp = maxHp;
        top = 0;
        this.player = player;
        width = ContextParameters.viewWidth/3;
        bottom = width/8;

        if(player)
        {
            left = 0;
            middle = left + width;
            right = middle;
            hitPointsRect = new Rect(left, top, middle, bottom);
            lostHitPointsRect = new Rect(middle, top, right, bottom);
        }
        else
        {
            right = ContextParameters.viewWidth;
            middle = right - width;
            left = middle;
            hitPointsRect = new Rect(middle, top, right, bottom);
            lostHitPointsRect = new Rect(left, top, middle, bottom);
        }

        hitPoints.setARGB(255,0,255,0);
        lostHitPoints.setARGB(100,50,50,50);
    }

    @Override
    public void drawObject(Canvas canvas) {
        canvas.drawRect(hitPointsRect, hitPoints);
        canvas.drawRect(lostHitPointsRect, lostHitPoints);
    }


    public void updateHp( int hp)
    {

        if(player) {
            middle = left + (int)(width/(float)maxHp * (float) hp);
        }
        else
        {
            middle = right - (int)(width/(float)maxHp * (float) hp);
        }

        int gradient = (int)(510/(float)maxHp*(float)hp);

        if(gradient >= 255)
        {
            red = 510 - gradient;
        }
        else
        {
            red = 255;
            green = gradient;
        }

        hitPoints.setARGB(255,red,green,0);

        if(player) {
            hitPointsRect.set(left, top, middle, bottom);
            lostHitPointsRect.set(middle, top, right, bottom);
        }
        else
        {
            hitPointsRect.set(middle, top, right, bottom);
            lostHitPointsRect.set(left, top, middle, bottom);
        }
        this.hp = hp;
    }
}
