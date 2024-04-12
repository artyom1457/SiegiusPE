package com.example.siegiuspe.GameObjects.Troops.Gaul;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SplashTroop;

import java.util.ArrayList;

public class MaceRider extends SplashTroop {
    public MaceRider(boolean player, int x, int y) {
        super(player, x, y);
        name = "mace rider";
        mSpeedPerSecond = 20 * (player ? 1:-1);
        mAttackDamage = 18;
        mMaxHealth = 130;
        mHealth = mMaxHealth;
        mEffectRange = 600;
        mEffectWidth = 300;
        mAttackDelay = 5;

        mCenterX = mCoordX;
        mCenterY = mCoordY;

        frameWidth = AnimationLibrary.maceRiderAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.maceRiderAnimation[0].getHeight()/2;

        actFrameCount = 5;
        dieFrameCount = 14;
        walkFrameCount = 21;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight/2,mMaxHealth);
    }

    @Override
    protected void die()
    {
        if(currentFrame < 9)
        {
            currentFrame ++;
        }
        else if(currentFrameTimer == 0)
        {
            currentFrameTimer = 80;
            currentFrame ++;
            if(currentFrame >= dieFrameCount)
            {
                mMarkedToRemoved = true;
                currentFrame --;
            }
        }
        else
        {
            currentFrameTimer--;
        }
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap troop = (flippedImage ? AnimationLibrary.maceRiderFlippedAnimation[currentFrame]
                : AnimationLibrary.maceRiderAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - (int)(frameWidth*0.44),mCoordY - (int)(frameHeight * 0.89),frameWidth,frameHeight));

        return addParticles(queue);
    }

    @Override
    protected void playAttack()
    {
        soundManager.playBoarRider();
    }
}
