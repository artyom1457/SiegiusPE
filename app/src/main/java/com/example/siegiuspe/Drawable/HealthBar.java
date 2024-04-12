package com.example.siegiuspe.Drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.siegiuspe.Drawable.DrawableObject;

public class HealthBar extends DrawableObject {
    private final Paint hitPoints = new Paint();
    private final Paint lostHitPoints = new Paint();

    private final Rect hitPointsRect;
    private final Rect lostHitPointsRect;

    private int hp;
    private final int maxHp;
    private final int height;

    private int left;
    private int middle;
    private int right;
    private int top;
    private int bottom;

    private int green = 255;
    private int red = 0;


    public HealthBar(int x,int y,int height,int maxHp)
    {
        this.height = height;
        left = x - 40;
        top = y - height;
        middle = left + (int)(80/(float)maxHp*(float)hp);
        bottom = y - height + 10;

        right = x + 40;

        this.maxHp = maxHp;
        this.hp = maxHp;

        hitPointsRect = new Rect(left,top,middle,bottom);
        lostHitPointsRect = new Rect(middle,top,right,bottom);

        hitPoints.setARGB(255,0,255,0);
        lostHitPoints.setARGB(100,50,50,50);

    }

    @Override
    public void drawObject(Canvas canvas) {
        canvas.drawRect(hitPointsRect, hitPoints);
        canvas.drawRect(lostHitPointsRect, lostHitPoints);
    }

    public void updateHp(int x, int y, int hp)
    {
        left = x - 40;
        top = y - height;
        middle = left + (int)(80/(float)maxHp*(float)hp);
        bottom = y - height + 10;

        right = x + 40;


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
        hitPointsRect.set(left,top,middle,bottom);
        lostHitPointsRect.set(middle,top,right,bottom);
        this.hp = hp;
    }

}
