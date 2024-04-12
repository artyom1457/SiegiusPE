package com.example.siegiuspe.GameObjects.Troops.Gaul;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SingleTargetTroop;

import java.util.ArrayList;

public class BoarRider extends SingleTargetTroop {


    public BoarRider(boolean player, int x, int y) {
        super(player, x, y);
        name = "boar rider";
        mSpeedPerSecond = 20 * (player ? 1:-1);
        mAttackDamage = 60;
        mMaxHealth = 180;
        mHealth = mMaxHealth;
        mEffectRange = 140;
        mEffectWidth = 100;
        mAttackDelay = 15;

        mCenterX = mCoordX;
        mCenterY = mCoordY;

        frameWidth = AnimationLibrary.boarRiderAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.boarRiderAnimation[0].getHeight()/2;

        actFrameCount = 6;
        dieFrameCount = 14;
        walkFrameCount = 21;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);
        //bottom = mCoordY + (int)(frameHeight * 0.03);
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
        Bitmap troop = (flippedImage ? AnimationLibrary.boarRiderFlippedAnimation[currentFrame]
                : AnimationLibrary.boarRiderAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - (int)(frameWidth*0.43),mCoordY - (int)(frameHeight * 0.91),frameWidth,frameHeight));

        return addParticles(queue);
    }

    @Override
    protected void playAttack()
    {
        soundManager.playBoarRider();
    }

}
