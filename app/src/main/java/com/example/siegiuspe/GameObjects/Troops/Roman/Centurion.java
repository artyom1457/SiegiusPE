package com.example.siegiuspe.GameObjects.Troops.Roman;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.RangedTroop;

import java.util.ArrayList;

public class Centurion extends RangedTroop {

    public Centurion(boolean player, int x, int y) {
        super(player, x, y,4);

        name = "centurion";
        mSpeedPerSecond = 7 * (player ? 1:-1);
        mAttackDamage = 30;
        mMaxHealth = 150;
        mHealth = mMaxHealth;
        mEffectRange = 700;
        mEffectWidth = 200;
        mAttackDelay = 15;

        mCenterX = mCoordX;
        mCenterY = mCoordY;

        frameWidth = AnimationLibrary.centurionAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.centurionAnimation[0].getHeight()/2;

        actFrameCount = 5;
        dieFrameCount = 10;
        walkFrameCount = 18;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap troop = (flippedImage ? AnimationLibrary.centurionFlippedAnimation[currentFrame]
                : AnimationLibrary.centurionAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - (int)(frameHeight * 0.91),frameWidth,frameHeight));

        return addParticles(queue);
    }
}
