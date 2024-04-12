package com.example.siegiuspe.GameObjects.Troops.Gaul;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SplashTroop;

import java.util.ArrayList;

public class Raider extends SplashTroop {

    public Raider(Boolean player, int cordX, int cordY) {
        super(player,cordX,cordY);

        name = "raider";
        mSpeedPerSecond = 10 * (player ? 1:-1);
        mAttackDamage = 20;
        mMaxHealth = 65;
        mHealth = mMaxHealth;
        mEffectRange = 80;
        mEffectWidth = 40;
        mAttackDelay = 10;

        mCenterX = mCoordX;
        mCenterY = mCoordY;
//        frameWidth = BaseObject.sSystemRegistry.animationLibrary.raiderAnimation[0][0].getWidth()/2;
//        frameHeight = BaseObject.sSystemRegistry.animationLibrary.raiderAnimation[0][0].getHeight()/2;

        frameWidth = AnimationLibrary.raiderAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.raiderAnimation[0].getHeight()/2;

//        walkFrameCount = BaseObject.sSystemRegistry.animationLibrary.raiderAnimation[0].length;
//        actFrameCount = BaseObject.sSystemRegistry.animationLibrary.raiderAnimation[1].length;
//        dieFrameCount = BaseObject.sSystemRegistry.animationLibrary.raiderAnimation[2].length;

        actFrameCount = 5;
        dieFrameCount = 10;
        walkFrameCount = 18;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);

    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue){
//        Bitmap bitmap = (flippedImage ? BaseObject.sSystemRegistry.animationLibrary.raiderFlippedAnimation[mState - 1][currentFrame]
//                : BaseObject.sSystemRegistry.animationLibrary.raiderAnimation[mState - 1][currentFrame] );

        Bitmap troop = (flippedImage ? AnimationLibrary.raiderFlippedAnimation[currentFrame]
                : AnimationLibrary.raiderAnimation[currentFrame] );

        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));
        return addParticles(queue);
    }
}
