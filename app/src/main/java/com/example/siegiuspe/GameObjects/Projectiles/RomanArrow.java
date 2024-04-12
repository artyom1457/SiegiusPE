package com.example.siegiuspe.GameObjects.Projectiles;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.GameObject;

import java.util.ArrayList;

public class RomanArrow extends Projectile{


    public RomanArrow(boolean player, int x, int y, GameObject target) {
        super(player, x, y, target);
        this.speed = (player ? 80 : -80);
        damage = 12;

        frameWidth = AnimationLibrary.RomanArrow.getWidth()/2;
        frameHeight = AnimationLibrary.RomanArrow.getHeight()/2;
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap projectile = (flippedImage ? AnimationLibrary.FlippedRomanArrow
                : AnimationLibrary.RomanArrow);
        queue.add(new DrawableBitmap(projectile,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));

        return queue;
    }
}
