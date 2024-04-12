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

public class RomanBase extends Fortress {

    public RomanBase(boolean player)
    {
        super(player);

        frameWidth = AnimationLibrary.romanBaseAnimation[0].getWidth();
        frameHeight = AnimationLibrary.romanBaseAnimation[0].getHeight();

        mCoordX = (mPlayer ? ContextParameters.bgX + frameWidth/2 : ContextParameters.bgX+ContextParameters.gameWidth - frameWidth/2);
        mCoordY = ContextParameters.viewHeight;

        extension = BitmapFactory.decodeResource(BaseObject.sSystemRegistry.contextParameters.context.getResources(),
                R.drawable.roman_base_extention);

        if(!player)
        {
            extension = Utils.createFlippedBitmap(extension,true,false);
        }
    }


    @Override
    public ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue) {
        Bitmap base = (flippedImage ? AnimationLibrary.romanBaseFlippedAnimation[currentFrame]
                : AnimationLibrary.romanBaseAnimation[currentFrame] );
        queue.add(new DrawableBitmap(base,mCoordX-frameWidth/2,mCoordY-frameHeight,frameWidth,frameHeight));

        return addHealthBar(queue);
    }
}
