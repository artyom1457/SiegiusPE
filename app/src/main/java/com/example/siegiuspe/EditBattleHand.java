package com.example.siegiuspe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siegiuspe.GameObjects.BaseObject;

import java.util.ArrayList;

public class EditBattleHand extends AppCompatActivity {



    private ImageButton caesar,vercing;
    private ArrayList<ImageView> select_troop;
    private ArrayList<ImageView> hud_troop;
    private ImageView showcase_troop;
    private TextView troop_name,troop_summery;

    private Button back,play;

    private int[] battleHandBarIDs;
    private int[] selectionBarIDs;
    private int commander;

    private int mapId;
    private int songId;
    private double topEdge;
    private double bottomEdge;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_battle_hand);

        BaseObject.sSystemRegistry.systemUiManager.changeDecorView(getWindow().getDecorView());
        BaseObject.sSystemRegistry.systemUiManager.hideSystemUIALL();

//        soundManager = new SoundManager();

        Intent intent = getIntent();
        mapId = intent.getExtras().getInt("BitmapImage",R.drawable.map_killing_fields);
        songId = intent.getExtras().getInt("songId",R.raw.music_army);
        topEdge = intent.getExtras().getDouble("TopEdge",500.0/681.0);
        bottomEdge = intent.getExtras().getDouble("BottomEdge",641.0/681.0);

        caesar = (ImageButton)findViewById(R.id.btn_select_caesar);
        vercing =  (ImageButton)findViewById(R.id.btn_select_vercing);

        loadSelect_troop();
        loadHud_troop();
        battleHandBarIDs = new int[5];
        selectionBarIDs = new int[6];

        showcase_troop = (ImageView)findViewById(R.id.battle_hand_iv_showcase);
        troop_name = (TextView) findViewById(R.id.battle_hand_name_showcase);
        troop_summery = (TextView) findViewById(R.id.battle_hand_summery_showcase);

        back = (Button)findViewById(R.id.selection_troops_back_button);
        play = (Button)findViewById(R.id.selection_troops_start_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(BaseObject.sSystemRegistry.contextParameters.context, GameBoard.class);
                    intent.putExtra("BitmapImage", mapId);
                    intent.putExtra("songId",songId);
                    intent.putExtra("TopEdge",topEdge);
                    intent.putExtra("BottomEdge",bottomEdge);
                    intent.putExtra("Money",100);
                    intent.putExtra("Economy",250);
                    loadIntentBattleHand(intent);
                    startActivityForResult(intent,1);
            }
        });

        sp=getSharedPreferences("select_troops_activity",0);
        commander = sp.getInt("select_commander",Utils.CAESAR);
        loadHandIDS();
        loadBattleHand();
        loadSelectionBar();

        caesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(commander == Utils.VERCING)
                {
                    saveCurrentHand();
                }
                commander = Utils.CAESAR;
                loadSelectionBar();
                loadHandIDS();
                loadBattleHand();

                showcase_troop.setBackgroundResource(R.drawable.caesar1);
                troop_name.setText(Utils.getTroopNameByID(Utils.CAESAR));
                troop_summery.setText(Utils.getTroopSummeryByID(Utils.CAESAR));
            }
        });

        vercing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(commander == Utils.CAESAR)
                {
                    saveCurrentHand();
                }
                commander = Utils.VERCING;
                loadSelectionBar();
                loadHandIDS();
                loadBattleHand();

                showcase_troop.setBackgroundResource(R.drawable.vercing1);
                troop_name.setText(Utils.getTroopNameByID(Utils.VERCING));
                troop_summery.setText(Utils.getTroopSummeryByID(Utils.VERCING));
            }
        });
    }

    private void loadHandIDS()
    {
        if(commander == Utils.CAESAR) {
            for (int i = 0; i < 5; i++) {
                battleHandBarIDs[i] = sp.getInt("select_roman_troop" + (i+1), i+1);
            }
        }
        else
        {
            for (int i = 0; i < 5; i++) {
                battleHandBarIDs[i] = sp.getInt("select_gaul_troop" + (i+1), i+8);
            }
        }
    }

    private void loadIntentBattleHand(Intent intent)
    {
        intent.putExtra("commander",commander);
        for (int i = 0; i < 5; i++) {
            intent.putExtra("hud_troop"+(i+1), battleHandBarIDs[i]);
        }
    }



    private void loadBattleHand()
    {
        for (int i = 0; i < 5; i++) {
            hud_troop.get(i).setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(battleHandBarIDs[i])));
        }
    }

    private void loadSelectionBar()
    {
        if(commander == Utils.CAESAR) {
            for (int i = 0; i < 6; i++) {
                select_troop.get(i).setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(i + 1)));
                selectionBarIDs[i] = i + 1;
            }
        }
        else
        {
            for (int i = 0; i < 6; i++) {
                select_troop.get(i).setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(i + 8)));
                selectionBarIDs[i] = i + 8;
            }
        }
    }

    private void loadSelect_troop()
    {
        select_troop = new ArrayList<ImageView>();
        for (int i = 0; i < 6; i++) {
            String ImageViewID = "IV_select_troop" + (i+1);
            int resID = getResources().getIdentifier(ImageViewID, "id", getPackageName());
            ImageView view = (ImageView)findViewById(resID);
            view.setOnLongClickListener(new MyLongClickListener());
            view.setOnClickListener(new myClickListener());
            select_troop.add(view);
        }
    }

    private void loadHud_troop()
    {
        hud_troop = new ArrayList<ImageView>();
        for (int i = 0; i < 5; i++) {
            String ImageViewID = "select_hud_troop" + (i+1);
            int resID = getResources().getIdentifier(ImageViewID, "id", getPackageName());
            ImageView view = (ImageView)findViewById(resID);
            view.setOnDragListener(new MyDragEventListener());
            hud_troop.add(view);
        }
    }

    private void saveCurrentHand()
    {
        SharedPreferences.Editor editor=sp.edit();
        if(commander == Utils.CAESAR) {
            for (int i = 0; i < 5; i++) {
                editor.putInt("select_roman_troop"+(i+1), battleHandBarIDs[i]);
            }
        }
        else
        {
            for (int i = 0; i < 5; i++) {
                editor.putInt("select_gaul_troop"+(i+1), battleHandBarIDs[i]);
            }
        }
        editor.putInt("select_commander",commander);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCurrentHand();
        BaseObject.sSystemRegistry.soundManager.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseObject.sSystemRegistry.soundManager.resumeMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    private class MyDragEventListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            final int action = event.getAction();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_LOCATION:
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DROP:
                    ImageView view = (ImageView)event.getLocalState();
                    v.setBackground(view.getBackground().getConstantState().newDrawable().mutate());
                    battleHandBarIDs[hud_troop.indexOf(v)] = selectionBarIDs[select_troop.indexOf(view)];
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    DebugLog.d("drag event","unknown event");
                    break;
            }

            return true;
        }
    }

    private class MyLongClickListener implements View.OnLongClickListener
    {
        @Override
        public boolean onLongClick(View v) {

            ClipData.Item item = new ClipData.Item("troop");
            ClipData dragData = new ClipData(
                    (CharSequence) v.getTag(),
                    new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN},item);

            View.DragShadowBuilder myShadow = new EditShadowBuilder(v, v.getBackground());
            v.startDragAndDrop(dragData,myShadow,v,0);
            return true;
        }
    }

    private class myClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            int troopID = selectionBarIDs[select_troop.indexOf(v)];
            showcase_troop.setBackground(v.getBackground().getConstantState().newDrawable().mutate());
            troop_name.setText(Utils.getTroopNameByID(troopID));
            troop_summery.setText(Utils.getTroopSummeryByID(troopID));
        }
    }


    private static class EditShadowBuilder extends View.DragShadowBuilder
    {
        private final Drawable shadow;
        private final int width;
        private final int height;
        final float scale = BaseObject.sSystemRegistry.contextParameters.context.getResources().getDisplayMetrics().density;

        public EditShadowBuilder(View v,Drawable img )
        {
            super(v);
            shadow = img.getConstantState().newDrawable().mutate();
            width = (int) (60 * scale + 0.5f);
            height = (int) (60 * scale + 0.5f);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            shadow.setBounds(0,0,width,height);
            outShadowSize.set(width,height);
            outShadowTouchPoint.set(width/2,height);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {

            shadow.draw(canvas);
        }
    }

}