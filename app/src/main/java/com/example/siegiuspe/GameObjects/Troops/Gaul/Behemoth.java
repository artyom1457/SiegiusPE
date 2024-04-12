package com.example.siegiuspe.GameObjects.Troops.Gaul;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SplashTroop;

import java.util.ArrayList;

public class Behemoth extends SplashTroop {

    public Behemoth(Boolean player, int cordX, int cordY) {
        super(player,cordX,cordY);

        name = "behemoth";
        mSpeedPerSecond = 5 * (player ? 1:-1);
        mAttackDamage = 50;
        mMaxHealth = 400;
        mHealth = mMaxHealth;
        mEffectRange = 200;
        mEffectWidth = 80;
        mAttackDelay = 20;

        mCenterX = mCoordX;
        mCenterY = mCoordY;
//        frameWidth = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[0][0].getWidth()/2;
//        frameHeight = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[0][0].getHeight()/2;

        frameWidth = AnimationLibrary.behemothAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.behemothAnimation[0].getHeight()/2;

//        walkFrameCount = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[0].length;
//        actFrameCount = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[1].length;
//        dieFrameCount = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[2].length;

        actFrameCount = 5;
        dieFrameCount = 10;
        walkFrameCount = 18;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);
    }



    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap troop = (flippedImage ? AnimationLibrary.behemothFlippedAnimation[currentFrame]
                : AnimationLibrary.behemothAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - (int)(frameWidth*0.333),mCoordY - (int)(frameHeight * 0.91),frameWidth,frameHeight));
        return addParticles(queue);
    }

    protected void playAttack()
    {
        soundManager.playWhop();
    }

    @Override
    protected boolean isAttack() {return currentFrame == 3;}
}
