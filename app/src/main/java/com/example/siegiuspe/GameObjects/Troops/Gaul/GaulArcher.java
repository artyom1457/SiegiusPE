package com.example.siegiuspe.GameObjects.Troops.Gaul;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.GameObjects.Troops.RangedTroop;

import java.util.ArrayList;

public class GaulArcher extends RangedTroop {
    public GaulArcher(boolean player, int x, int y) {
        super(player, x, y,3);

        name = "gaul archer";
        mSpeedPerSecond = 10 * (player ? 1:-1);
        mAttackDamage = 18;
        mMaxHealth = 65;
        mHealth = mMaxHealth;
        mEffectRange = 900;
        mEffectWidth = 200;
        mAttackDelay = 15;

        mCenterX = mCoordX;
        mCenterY = mCoordY;

        frameWidth = AnimationLibrary.gaulArcherAnimation[0].getWidth()/2;
        frameHeight = AnimationLibrary.gaulArcherAnimation[0].getHeight()/2;

        actFrameCount = 5;
        dieFrameCount = 10;
        walkFrameCount = 18;

        currentFrame = dieFrameCount;

        healthBar = new HealthBar(mCoordX,mCoordY,frameHeight,mMaxHealth);
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap troop = (flippedImage ? AnimationLibrary.gaulArcherFlippedAnimation[currentFrame]
                : AnimationLibrary.gaulArcherAnimation[currentFrame] );
        queue.add(new DrawableBitmap(troop,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));

        return addParticles(queue);
    }
}
