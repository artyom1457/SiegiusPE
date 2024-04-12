package com.example.siegiuspe.GameObjects.Projectiles;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.GameObject;

import java.util.ArrayList;

public class Spear extends Projectile {
    public Spear(boolean player, int x, int y, GameObject target) {
        super(player, x, y, target);
        this.speed = (player ? 60 : -60);
        damage = 30;

        frameWidth = AnimationLibrary.Spear.getWidth()/2;
        frameHeight = AnimationLibrary.Spear.getHeight()/2;
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap projectile = (flippedImage ? AnimationLibrary.FlippedSpear
                : AnimationLibrary.Spear);
        queue.add(new DrawableBitmap(projectile,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));

        return queue;
    }
}
