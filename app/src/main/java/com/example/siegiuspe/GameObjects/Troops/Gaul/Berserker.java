package com.example.siegiuspe.GameObjects.Troops.Gaul;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.SingleTargetTroop;

import java.util.ArrayList;

public class Berserker extends SingleTargetTroop {

    public Berserker(Boolean player, int cordX, int cordY) {
        super(player,cordX,cordY);

        name = "berserker";
        mSpeedPerSecond = 10 * (player ? 1:-1);
        mAttackDamage = 11;
        mMaxHealth = 100;
        mHealth = mMaxHealth;
        mEffectRange = 80;
        mEffectWidth = 80;
        mAttackDelay = 0;

        mCenterX = mCoordX;
        mCenterY = mCoordY;
//        frameWidth = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[0][0].getWidth()/2;
//        frameHeight = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[0][0].getHeight()/2;

        frameWidth = AnimationLibrary.berserkerAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.berserkerAnimation[0].getHeight()/2;

//        walkFrameCount = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[0].length;
//        actFrameCount = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[1].length;
//        dieFrameCount = BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[2].length;

        actFrameCount = 9;
        dieFrameCount = 14;
        walkFrameCount = 22;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue)
    {
//        Bitmap bitmap = (flippedImage ? BaseObject.sSystemRegistry.animationLibrary.berserkFlippedAnimation[mState - 1][currentFrame]
//                : BaseObject.sSystemRegistry.animationLibrary.berserkerAnimation[mState - 1][currentFrame] );

        Bitmap troop = (flippedImage ? AnimationLibrary.berserkFlippedAnimation[currentFrame]
                : AnimationLibrary.berserkerAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - (int)(frameHeight * 0.92),frameWidth,frameHeight));
        return addParticles(queue);
    }

    @Override
    protected boolean isAttack() {return (currentFrame == 4 || currentFrame == 8);}
}
