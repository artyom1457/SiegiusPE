package com.example.siegiuspe.GameObjects.Troops.Gaul;

import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.GameObjects.Projectiles.Projectile;

import java.util.ArrayList;

public class GaulArrow extends Projectile {
    public GaulArrow(boolean player, int x, int y, GameObject target) {
        super(player, x, y, target);
        this.speed = (player ? 70 : -70);
        damage = 18;

        frameWidth = AnimationLibrary.GaulArrow.getWidth()/2;
        frameHeight = AnimationLibrary.GaulArrow.getHeight()/2;
    }

    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap projectile = (flippedImage ? AnimationLibrary.FlippedGaulArrow
                : AnimationLibrary.GaulArrow);
        queue.add(new DrawableBitmap(projectile,mCoordX - frameWidth/2,mCoordY - frameHeight,frameWidth,frameHeight));

        return queue;
    }
}
