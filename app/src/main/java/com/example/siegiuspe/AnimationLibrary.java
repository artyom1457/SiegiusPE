package com.example.siegiuspe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.siegiuspe.GameObjects.BaseObject;

public class AnimationLibrary {
    public static Bitmap [] legionaryAnimation;
    public static Bitmap [] legionaryFlippedAnimation;
    public static Bitmap [] defenderAnimation;
    public static Bitmap [] defenderFlippedAnimation;
    public static Bitmap [] romanArcherAnimation;
    public static Bitmap [] romanArcherFlippedAnimation;
    public static Bitmap [] romanFireArcherAnimation;
    public static Bitmap [] romanFireArcherFlippedAnimation;
    public static Bitmap [] romanCavalryAnimation;
    public static Bitmap [] romanCavalryFlippedAnimation;
    public static Bitmap [] centurionAnimation;
    public static Bitmap [] centurionFlippedAnimation;
    public static Bitmap [] caesarAnimation;
    public static Bitmap [] caesarFlippedAnimation;
    public static Bitmap [] romanBaseAnimation;
    public static Bitmap [] romanBaseFlippedAnimation;

    public static Bitmap [] berserkerAnimation;
    public static Bitmap [] berserkFlippedAnimation;
    public static Bitmap [] raiderAnimation;
    public static Bitmap [] raiderFlippedAnimation;
    public static Bitmap [] behemothAnimation;
    public static Bitmap [] behemothFlippedAnimation;
    public static Bitmap [] boarRiderAnimation;
    public static Bitmap [] boarRiderFlippedAnimation;
    public static Bitmap [] maceRiderAnimation;
    public static Bitmap [] maceRiderFlippedAnimation;
    public static Bitmap [] gaulArcherAnimation;
    public static Bitmap [] gaulArcherFlippedAnimation;
    public static Bitmap [] vercingAnimation;
    public static Bitmap [] vercingFlippedAnimation;
    public static Bitmap [] gaulBaseAnimation;
    public static Bitmap [] gaulBaseFlippedAnimation;

    public static Bitmap RomanArrow;
    public static Bitmap FlippedRomanArrow;
    public static Bitmap RomanFireArrow;
    public static Bitmap FlippedRomanFireArrow;
    public static Bitmap GaulArrow;
    public static Bitmap FlippedGaulArrow;
    public static Bitmap Spear;
    public static Bitmap FlippedSpear;

    public static Bitmap [] bloodSmallAnimation;
    public static Bitmap [] bloodSmallFlippedAnimation;

    public static Bitmap [] fireAnimation;

    public static int bloodFrameHeight;
    public static int bloodFrameWidth;

    public static int fireFrameHeight;
    public static int fireFrameWidth;


    protected Context mContext;

    public AnimationLibrary()
    {
        mContext = BaseObject.sSystemRegistry.contextParameters.context;
    }

    private Bitmap [] loadAnimation(String name,int size,boolean flipped)
    {
        Bitmap [] animation = new Bitmap[size];
        for(int i = 0 ; i < size ; i++)
        {
            animation[i] = BitmapFactory.decodeResource(mContext.getResources(),getDrawableIDByString(name + (i + 1)));
            if(flipped) {
                animation[i] = Utils.createFlippedBitmap(animation[i],true,false);
            }
        }
        return animation;
    }

//    private Bitmap [] loadTroopAnimation(String name,int walk,int act,int die,boolean flipped)
//    {
//        Bitmap [][] animation = new Bitmap[3][];
//
//        animation[0] = loadAnimation(name + "_walk",walk,flipped);
//        animation[1] = loadAnimation(name + "_act",act,flipped);
//        animation[2] = loadAnimation(name + "_die",die,flipped);
//
//        return animation;
//    }


    private int getDrawableIDByString(String name)
    {
        Resources resources = mContext.getResources();
        return resources.getIdentifier(name, "drawable",
                mContext.getPackageName());
    }

    public void loadBerserkerAnimation()
    {
        berserkerAnimation = loadAnimation("berserker",22,false);
        berserkFlippedAnimation = loadAnimation("berserker",22,true);
    }

    public void loadDefenderAnimation()
    {
        defenderAnimation = loadAnimation("defender",18,false);
        defenderFlippedAnimation = loadAnimation("defender",18,true);
    }

    public void loadRomanArcherAnimation()
    {
        romanArcherAnimation = loadAnimation("roman_archer",18,false);
        romanArcherFlippedAnimation = loadAnimation("roman_archer",18,true);
    }

    public void loadRomanFireArcherAnimation()
    {
        romanFireArcherAnimation = loadAnimation("roman_fire_archer",18,false);
        romanFireArcherFlippedAnimation = loadAnimation("roman_fire_archer",18,true);
    }

    public void loadCenturionAnimation()
    {
        centurionAnimation = loadAnimation("centurion",18,false);
        centurionFlippedAnimation = loadAnimation("centurion",18,true);
    }

    public void loadCaesarAnimation()
    {
        caesarAnimation = loadAnimation("caesar",12,false);
        caesarFlippedAnimation = loadAnimation("caesar",12,true);
    }

    public void loadLegionaryAnimation()
    {
        legionaryAnimation = loadAnimation("legionary",18,false);
        legionaryFlippedAnimation = loadAnimation("legionary",18,true);
    }

    public void loadRaiderAnimation()
    {
        raiderAnimation = loadAnimation("raider",18,false);
        raiderFlippedAnimation = loadAnimation("raider",18,true);
    }

    public void loadRomanCavalryAnimation()
    {
        romanCavalryAnimation = loadAnimation("roman_cavalry",21,false);
        romanCavalryFlippedAnimation = loadAnimation("roman_cavalry",21,true);
    }
    public void loadBehemothAnimation()
    {
        behemothAnimation = loadAnimation("behemoth",18,false);
        behemothFlippedAnimation = loadAnimation("behemoth",18,true);
    }

    public void loadBoarRiderAnimation()
    {
        boarRiderAnimation = loadAnimation("boar_rider",21,false);
        boarRiderFlippedAnimation = loadAnimation("boar_rider",21,true);
    }

    public void loadMaceRiderAnimation()
    {
        maceRiderAnimation = loadAnimation("mace_rider",21,false);
        maceRiderFlippedAnimation = loadAnimation("mace_rider",21,true);

    }

    public void loadGaulArcherAnimation()
    {
        gaulArcherAnimation = loadAnimation("gaul_archer",18,false);
        gaulArcherFlippedAnimation = loadAnimation("gaul_archer",18,true);
    }

    public void loadRomanBaseAnimation()
    {
        romanBaseAnimation = loadAnimation("roman_base",5,false);
        romanBaseFlippedAnimation = loadAnimation("roman_base",5,true);
    }

    public void loadGaulBaseAnimation()
    {
        gaulBaseAnimation = loadAnimation("gaul_base",5,false);
        gaulBaseFlippedAnimation = loadAnimation("gaul_base",5,true);
    }

    public void loadVercingAnimation()
    {
        vercingAnimation = loadAnimation("vercing",15,false);
        vercingFlippedAnimation = loadAnimation("vercing",15,true);
    }



    public void loadBloodSmallAnimation()
    {

        bloodSmallAnimation = loadAnimation("blood_small",11,false);
        bloodSmallFlippedAnimation = loadAnimation("blood_small",11,true);
        bloodFrameHeight = bloodSmallAnimation[0].getHeight()/2;
        bloodFrameWidth = bloodSmallAnimation[0].getWidth()/2;
    }


    public void loadFireAnimation()
    {
        fireAnimation = loadAnimation("fire",4,false);
        fireFrameHeight = fireAnimation[0].getHeight()/2;
        fireFrameWidth = fireAnimation[0].getWidth()/2;
    }

    public void loadProjectiles()
    {
        RomanArrow = BitmapFactory.decodeResource(mContext.getResources(),getDrawableIDByString("roman_arrow"));
        FlippedRomanArrow = Utils.createFlippedBitmap(RomanArrow,true,false);
        RomanFireArrow = BitmapFactory.decodeResource(mContext.getResources(),getDrawableIDByString("roman_fire_arrow"));
        FlippedRomanFireArrow = Utils.createFlippedBitmap(RomanFireArrow,true,false);
        GaulArrow = BitmapFactory.decodeResource(mContext.getResources(),getDrawableIDByString("gaul_arrow"));
        FlippedGaulArrow = Utils.createFlippedBitmap(GaulArrow,true,false);
        Spear = BitmapFactory.decodeResource(mContext.getResources(),getDrawableIDByString("spear"));
        FlippedSpear = Utils.createFlippedBitmap(Spear,true,false);
    }
}
