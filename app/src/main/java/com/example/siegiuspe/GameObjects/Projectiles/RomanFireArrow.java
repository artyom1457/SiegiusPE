package com.example.siegiuspe.GameObjects.Projectiles;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.GameObject;

import java.util.ArrayList;

public class RomanFireArrow extends Projectile {

    public RomanFireArrow(boolean player, int x, int y, GameObject target) {
        super(player, x, y, target);
        this.speed = (player ? 80 : -80);
        damage = 20;

        frameWidth = AnimationLibrary.RomanFireArrow.getWidth()/2;
        frameHeight = AnimationLibrary.RomanFireArrow.getHeight()/2;
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap projectile = (flippedImage ? AnimationLibrary.FlippedRomanFireArrow
                : AnimationLibrary.RomanFireArrow);
        queue.add(new DrawableBitmap(projectile,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));

        return queue;
    }
}
