package com.example.siegiuspe.GameObjects.Troops.Roman;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SplashTroop;

import java.util.ArrayList;

public class RomanCavalry extends SplashTroop {
    public RomanCavalry(Boolean player, int cordX, int cordY) {
        super(player,cordX,cordY);

        name = "roman_cavalry";
        mSpeedPerSecond = 30 * (player ? 1:-1);
        mAttackDamage = 30;
        mMaxHealth = 200;
        mHealth = mMaxHealth;
        mEffectRange = 200;
        mEffectWidth = 200;
        mAttackDelay = 15;

        mCenterX = mCoordX;
        mCenterY = mCoordY;

//        frameWidth = BaseObject.sSystemRegistry.animationLibrary.romanCavalryAnimation[0][0].getWidth()/2;
//        frameHeight = BaseObject.sSystemRegistry.animationLibrary.romanCavalryAnimation[0][0].getHeight()/2;
        frameWidth = AnimationLibrary.romanCavalryAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.romanCavalryAnimation[0].getHeight()/2;


//        walkFrameCount = BaseObject.sSystemRegistry.animationLibrary.romanCavalryAnimation[0].length;
//        actFrameCount = BaseObject.sSystemRegistry.animationLibrary.romanCavalryAnimation[1].length;
//        dieFrameCount = BaseObject.sSystemRegistry.animationLibrary.romanCavalryAnimation[2].length;

        actFrameCount = 6;
        dieFrameCount = 14;
        walkFrameCount = 21;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);

    }

    protected void playAttack()
    {
        if (Math.random() < 0.5) soundManager.playCut();
        else soundManager.playHorse();
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
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue){
//        Bitmap bitmap = (flippedImage ? BaseObject.sSystemRegistry.animationLibrary.romanCavalryFlippedAnimation[mState - 1][currentFrame]
//                : BaseObject.sSystemRegistry.animationLibrary.romanCavalryAnimation[mState - 1][currentFrame] );

        Bitmap troop = (flippedImage ? AnimationLibrary.romanCavalryFlippedAnimation[currentFrame]
                : AnimationLibrary.romanCavalryAnimation[currentFrame] );

        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - (int)(frameHeight * 0.95),frameWidth,frameHeight));

        return addParticles(queue);
    }

}
