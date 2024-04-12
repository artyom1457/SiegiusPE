package com.example.siegiuspe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.siegiuspe.GameObjects.Troops.Troop;
import com.example.siegiuspe.GameObjects.Troops.Gaul.Behemoth;
import com.example.siegiuspe.GameObjects.Troops.Gaul.Berserker;
import com.example.siegiuspe.GameObjects.Troops.Gaul.BoarRider;
import com.example.siegiuspe.GameObjects.Troops.Roman.Centurion;
import com.example.siegiuspe.GameObjects.Troops.Roman.Defender;
import com.example.siegiuspe.GameObjects.Troops.Gaul.GaulArcher;
import com.example.siegiuspe.GameObjects.Troops.Roman.Legionary;
import com.example.siegiuspe.GameObjects.Troops.Gaul.MaceRider;
import com.example.siegiuspe.GameObjects.Troops.Gaul.Raider;
import com.example.siegiuspe.GameObjects.Troops.Roman.RomanArcher;
import com.example.siegiuspe.GameObjects.Troops.Roman.RomanCavalry;
import com.example.siegiuspe.GameObjects.Troops.Roman.RomanFireArcher;

import java.util.Random;

public class Utils {

    public static int FPS = 200;
    public static final Paint dmgPaint = new Paint();
    public static int MAX_FPS = 30;

    public static final int
            LEGIONARY = 1,DEFENDER = 2, ROMAN_CAVALRY = 3, ROMAN_ARCHER = 4,CENTURION = 5,
            ROMAN_FIRE_ARCHER = 6,CAESAR = 7,
            RAIDER = 8,BERSERKER = 9, BEHEMOTH = 10, BOAR_RIDER = 11, MACE_RIDER = 12, GAUL_ARCHER = 13,
            VERCING = 14;


    public static int getResourceIDByString(Context context,String name,String defType)
    {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, defType,
                context.getPackageName());
        return resourceId;
    }

    public static Bitmap createFlippedBitmap(Bitmap source, boolean xFlip, boolean yFlip) {
        Matrix matrix = new Matrix();
        matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }



    public static synchronized void drawBitmap(Canvas canvas,Bitmap bitmap,int x, int y, int width, int height)
    {
        Rect source = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect target = new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bitmap, source, target, null);
    }


    public static int getRandomFromArray(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }


    public static Bitmap getTroopBitmapById(int id)
    {
        Bitmap bitmap;
        switch(id)
        {
            case Utils.LEGIONARY:
                bitmap = AnimationLibrary.legionaryAnimation[0];
                break;
            case Utils.DEFENDER:
                bitmap = AnimationLibrary.defenderAnimation[0];
                break;
            case Utils.ROMAN_CAVALRY:
                bitmap = AnimationLibrary.romanCavalryAnimation[0];
                break;

            case Utils.ROMAN_ARCHER:
                bitmap = AnimationLibrary.romanArcherAnimation[0];
                break;

            case Utils.CENTURION:
                bitmap = AnimationLibrary.centurionAnimation[0];
                break;

            case Utils.ROMAN_FIRE_ARCHER:
                bitmap = AnimationLibrary.romanFireArcherAnimation[0];
                break;

            case Utils.CAESAR:
                bitmap = AnimationLibrary.caesarAnimation[0];
                break;

            case Utils.RAIDER:
                bitmap = AnimationLibrary.raiderAnimation[0];
                break;
            case Utils.BERSERKER:
                bitmap = AnimationLibrary.berserkerAnimation[0];
                break;
            case Utils.BEHEMOTH:
                bitmap = AnimationLibrary.behemothAnimation[0];
                break;

            case Utils.BOAR_RIDER:
                bitmap = AnimationLibrary.boarRiderAnimation[0];
                break;

            case Utils.MACE_RIDER:
                bitmap = AnimationLibrary.maceRiderAnimation[0];
                break;

            case Utils.GAUL_ARCHER:
                bitmap = AnimationLibrary.gaulArcherAnimation[0];
                break;

            case Utils.VERCING:
                bitmap = AnimationLibrary.vercingAnimation[0];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return bitmap ;
    }

    public static Troop getTroopById(int id, boolean player, int x, int y)
    {
        Troop troop;
        switch(id)
        {
            case Utils.LEGIONARY:
                troop = new Legionary(player,x,y);
                break;
            case Utils.DEFENDER:
                troop = new Defender(player,x,y);
                break;
            case Utils.ROMAN_CAVALRY:
                troop = new RomanCavalry(player,x,y);
                break;

            case Utils.ROMAN_ARCHER:
                troop = new RomanArcher(player,x,y);
                break;

            case Utils.CENTURION:
                troop = new Centurion(player,x,y);
                break;

            case Utils.ROMAN_FIRE_ARCHER:
                troop = new RomanFireArcher(player,x,y);
                break;

            case Utils.CAESAR:
                troop = new RomanCavalry(player,x,y);
                break;

            case Utils.RAIDER:
                troop = new Raider(player,x,y);
                break;
            case Utils.BERSERKER:
                troop = new Berserker(player,x,y);
                break;
            case Utils.BEHEMOTH:
                troop = new Behemoth(player,x,y);
                break;

            case Utils.BOAR_RIDER:
                troop = new BoarRider(player,x,y);
                break;

            case Utils.MACE_RIDER:
                troop = new MaceRider(player,x,y);
                break;

            case Utils.GAUL_ARCHER:
                troop = new GaulArcher(player,x,y);
                break;

            case Utils.VERCING:
                troop = new Raider(player,x,y);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return troop ;
    }

    public static int getTroopCostById(int id)
    {
        int cost = 0;
        switch(id)
        {
            case Utils.LEGIONARY:
                cost = 55;
                break;
            case Utils.DEFENDER:
                cost = 75;
                break;
            case Utils.ROMAN_CAVALRY:
                cost = 170;
                break;
            case Utils.ROMAN_ARCHER:
                cost = 50;
                break;
            case Utils.CENTURION:
                cost = 135;
                break;
            case Utils.ROMAN_FIRE_ARCHER:
                cost = 80;
                break;
            case Utils.CAESAR:
                cost = 250;
                break;
            case Utils.RAIDER:
                cost = 65;
                break;
            case Utils.BERSERKER:
                cost = 90;
                break;
            case Utils.BEHEMOTH:
                cost = 250;
                break;
            case Utils.BOAR_RIDER:
                cost = 180;
                break;
            case Utils.MACE_RIDER:
                cost = 160;
                break;
            case Utils.GAUL_ARCHER:
                cost = 50;
                break;
            case Utils.VERCING:
                cost = 250;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return cost ;
    }

    public static double[] getOffsetsByID(int id)
    {
        switch(id)
        {
            case Utils.LEGIONARY:
            case Utils.RAIDER:
            case Utils.ROMAN_ARCHER:
            case Utils.DEFENDER:
            case Utils.GAUL_ARCHER:
                return new double[]{0.5, 1};
            case Utils.ROMAN_CAVALRY:
                return new double[]{0.5, 0.95};
            case Utils.CENTURION:
                return new double[]{0.5, 0.91};
            case Utils.ROMAN_FIRE_ARCHER:
                return new double[]{0.5, 0.86};
            case Utils.CAESAR:
                return new double[]{0.64, 0.97};
            case Utils.BERSERKER:
                return new double[]{0.5, 0.92};
            case Utils.BEHEMOTH:
                return new double[]{0.333, 0.91};
            case Utils.BOAR_RIDER:
                return new double[]{0.43, 0.91};
            case Utils.MACE_RIDER:
                return new double[]{0.44, 0.89};
            case Utils.VERCING:
                return new double[]{0.5, 0.97};
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
    }

    public static String getTroopNameByID(int id)
    {
        String name;
        switch(id)
        {
            case Utils.LEGIONARY:
                name = "Legionary";
                break;
            case Utils.DEFENDER:
                name = "Defender";
                break;
            case Utils.ROMAN_CAVALRY:
                name = "Cavalry";
                break;
            case Utils.ROMAN_ARCHER:
            case Utils.GAUL_ARCHER:
                name = "Archer";
                break;
            case Utils.CENTURION:
                name = "Centurion";
                break;
            case Utils.ROMAN_FIRE_ARCHER:
                name = "Fire Archer";
                break;
            case Utils.CAESAR:
                name = "Caesar";
                break;
            case Utils.RAIDER:
                name = "Raider";
                break;
            case Utils.BERSERKER:
                name = "Berserker";
                break;
            case Utils.BEHEMOTH:
                name = "Behemoth";
                break;
            case Utils.BOAR_RIDER:
                name = "Boar Rider";
                break;
            case Utils.MACE_RIDER:
                name = "Mace Rider";
                break;
            case Utils.VERCING:
                name = "Vercing";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return name ;
    }

    public static String getTroopSummeryByID(int id)
    {
        String description;
        switch(id)
        {
            case Utils.LEGIONARY:
                description = "The primary Infantry unit for the roman" +
                        " legion.\nattack: 14\nhealth: 65\nmovement" +
                        " speed: 10\ntype: Single target";
                break;
            case Utils.DEFENDER:
                description = "The primary Defensive unit for the roman" +
                        " legion.\nattack: 10\nhealth: 180\nmovement" +
                        " speed: 7\ntype: Splash target";
                break;
            case Utils.ROMAN_CAVALRY:
                description = "Fast,with Heavy Armor and a spear attack that hits multiple enemies." +
                        " \nattack: 30\nhealth: 200\nmovement" +
                        " speed: 30\ntype: Splash target";
                break;
            case Utils.ROMAN_ARCHER:
                description = "The primary Ranged unit for the roman" +
                        " legion.\nattack: 12\nhealth: 45\nmovement" +
                        " speed: 10\nrange: 900" +
                        "type: Range Single target";
                break;
            case Utils.CENTURION:
                description = "Heavy Armor with a large shield and Powerful Ranged spear attacks." +
                        " \nattack: 30\nhealth: 150\nmovement" +
                        " speed: 7\nrange: 700" +
                        "type: Range Single target";
                break;
            case Utils.ROMAN_FIRE_ARCHER:
                description = "Medium armor and arrows that set enemies on Fire, dealing Damage over time." +
                        " \nattack: 20\nhealth: 65\nmovement" +
                        " speed: 10\nrange: 900" +
                        "type: Range Single target";
                break;
            case Utils.CAESAR:
                description = "Caesar is the General of the Roman Legion." +
                    " \nattack: 30\nhealth: 200\nmovement" +
                    " speed: 30\ntype: Single target";
                break;
            case Utils.RAIDER:
                description = "The primary Infantry unit for the Gallic tribes." +
                        " \nattack: 20\nhealth: 65\nmovement" +
                        " speed: 10\ntype: Splash target";
                break;
            case Utils.BERSERKER:
                description = "Medium armor and Fast, Powerful melee attacks." +
                    " \nattack: 11\nhealth: 100\nmovement" +
                    " speed: 10\ntype: Single target";
                break;
            case Utils.BEHEMOTH:
                description = "A Devastating melee attack that hits Multiple enemies and Massive armor." +
                        " \nattack: 50\nhealth: 400\nmovement" +
                        " speed: 5\ntype: Splash target";
                break;
            case Utils.BOAR_RIDER:
                description = "Heavy Armor and a Powerful Hammer attack that hits Huge single target damage." +
                        " \nattack: 60\nhealth: 180\nmovement" +
                        " speed: 20\ntype: Single target";
                break;
            case Utils.MACE_RIDER:
                description = "Medium Armor and a Mace attack that Damages All enemies in its path." +
                        " \nattack: 18\nhealth: 130\nmovement" +
                        " speed: 20\nrange: 600" +
                        "type: Range Splash target";
                break;
            case Utils.GAUL_ARCHER:
                description = "The primary Ranged unit for the Gallic tribes" +
                        " legion.\nattack: 18\nhealth: 65\nmovement" +
                        " speed: 10\ntype: Single target";
                break;
            case Utils.VERCING:
                description = "Vercing is the General of the Gallic tribes." +
                        " \nattack: 30\nhealth: 200\nmovement" +
                        " speed: 30\ntype: Single target";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return description ;
    }
}
