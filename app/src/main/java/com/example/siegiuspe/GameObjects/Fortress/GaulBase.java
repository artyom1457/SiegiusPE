package com.example.siegiuspe.GameObjects.Fortress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.R;
import com.example.siegiuspe.Utils;

import java.util.ArrayList;

public class GaulBase extends Fortress {

    public GaulBase(boolean player)
    {
        super(player);

        frameWidth = AnimationLibrary.gaulBaseAnimation[0].getWidth();
        frameHeight = AnimationLibrary.gaulBaseAnimation[0].getHeight();

        mCoordX = (mPlayer ? ContextParameters.bgX + frameWidth/2 : ContextParameters.bgX+ContextParameters.gameWidth - frameWidth/2);
        mCoordY = ContextParameters.viewHeight;

        mCenterY = mCoordY;
        mCenterX = mCoordX;

        extension = BitmapFactory.decodeResource(BaseObject.sSystemRegistry.contextParameters.context.getResources(),
                R.drawable.gaul_base_extention);

        if(!player)
        {
            extension = Utils.createFlippedBitmap(extension,true,false);
        }
    }


    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap base = (flippedImage ? AnimationLibrary.gaulBaseFlippedAnimation[currentFrame]
                : AnimationLibrary.gaulBaseAnimation[currentFrame] );

        queue.add(new DrawableBitmap(base,mCoordX-frameWidth/2,mCoordY-frameHeight,frameWidth,frameHeight));

        return addHealthBar(queue);
    }
}
