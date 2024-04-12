package com.example.siegiuspe.GameObjects.Troops.Roman;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.RangedTroop;

import java.util.ArrayList;

public class RomanFireArcher extends RangedTroop {

    public RomanFireArcher(boolean player, int x, int y) {
        super(player, x, y,2);

        name = "roman fire archer";
        mSpeedPerSecond = 10 * (player ? 1:-1);
        mAttackDamage = 20;
        mMaxHealth = 65;
        mHealth = mMaxHealth;
        mEffectRange = 900;
        mEffectWidth = 200;
        mAttackDelay = 15;

        mCenterX = mCoordX;
        mCenterY = mCoordY;

        frameWidth = AnimationLibrary.romanFireArcherAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.romanFireArcherAnimation[0].getHeight()/2;

        actFrameCount = 5;
        dieFrameCount = 10;
        walkFrameCount = 18;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap troop = (flippedImage ? AnimationLibrary.romanFireArcherFlippedAnimation[currentFrame]
                : AnimationLibrary.romanFireArcherAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - (int)(frameHeight * 0.86),frameWidth,frameHeight));

        return addParticles(queue);
    }
}
