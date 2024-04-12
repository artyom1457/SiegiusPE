package com.example.siegiuspe.GameObjects.Troops.Roman;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SingleTargetTroop;

import java.util.ArrayList;

public class Legionary extends SingleTargetTroop {

    public Legionary(Boolean player, int cordX, int cordY) {
        super(player,cordX,cordY);

        name = "legionary";
        mSpeedPerSecond = 10 * (player ? 1:-1);
        mAttackDamage = 14;
        mMaxHealth = 65;
        mHealth = mMaxHealth;
        mEffectRange = 80;
        mEffectWidth = 40;
        mAttackDelay = 10;

        mCenterX = mCoordX;
        mCenterY = mCoordY;
//        frameWidth = BaseObject.sSystemRegistry.animationLibrary.legionaryAnimation[0][0].getWidth()/2;
//        frameHeight = BaseObject.sSystemRegistry.animationLibrary.legionaryAnimation[0][0].getHeight()/2;

        frameWidth = AnimationLibrary.legionaryAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.legionaryAnimation[0].getHeight()/2;

//        walkFrameCount = BaseObject.sSystemRegistry.animationLibrary.legionaryAnimation[0].length;
//        actFrameCount = BaseObject.sSystemRegistry.animationLibrary.legionaryAnimation[1].length;
//        dieFrameCount = BaseObject.sSystemRegistry.animationLibrary.legionaryAnimation[2].length;

        actFrameCount = 5;
        dieFrameCount = 10;
        walkFrameCount = 18;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);

    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue)
    {

        Bitmap troop = (flippedImage ? AnimationLibrary.legionaryFlippedAnimation[currentFrame]
                : AnimationLibrary.legionaryAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));

        return addParticles(queue);
    }
}
