package com.example.siegiuspe.GameObjects.Troops.Roman;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SplashTroop;

import java.util.ArrayList;

public class Defender extends SplashTroop {


    public Defender(boolean player, int x, int y) {
        super(player, x, y);

        name = "defender";
        mSpeedPerSecond = 7 * (player ? 1:-1);
        mAttackDamage = 10;
        mMaxHealth = 180;
        mHealth = mMaxHealth;
        mEffectRange = 80;
        mEffectWidth = 80;
        mAttackDelay = 10;

        mCenterX = mCoordX;
        mCenterY = mCoordY;

        frameWidth = AnimationLibrary.defenderAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.defenderAnimation[0].getHeight()/2;

        actFrameCount = 5;
        dieFrameCount = 10;
        walkFrameCount = 18;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap troop = (flippedImage ? AnimationLibrary.defenderFlippedAnimation[currentFrame]
                : AnimationLibrary.defenderAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));

        return addParticles(queue);
    }

    protected void playAttack()
    {
        soundManager.playBlunt();
    }
}
