package com.example.siegiuspe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.Managers.SoundManager;
import com.example.siegiuspe.Managers.SystemUiManager;

public class SelectionRoom extends AppCompatActivity {

    private Gallery levels;
    private ImageView level_show;
    private TextView level_name_TV;
    private Button play;
    private ImageButton backButton;
    private int map_selected;
//    private SoundManager soundManager;

    private static final String[] level_names =
            {
                    "Killing Fields",
                    "Island",
                    "Island (Dawn)",
                    "Over Pass (Dawn)",
                    "Over Pass (Night)",
                    "Volcano Pass",
                    "Mountain Pass",
                    "Tundra",
                    "Tundra (Night)",
                    "Frozen Forest",
                    "Forest",
                    "Forest (Night)"
            };

    private static final int[] level_songs =
            {
                    SoundManager.ARMY,
                    SoundManager.ARMY2,
                    SoundManager.GATE2,
                    SoundManager.CHAOTIC,
                    SoundManager.GATE2,
                    SoundManager.GATE2,
                    SoundManager.EGYPT,
                    SoundManager.INTRO,
                    SoundManager.INTRO,
                    SoundManager.GATE,
                    SoundManager.GATE2,
                    SoundManager.GATE2
            };

    private static final int[] level_images_ids =
            {
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields,
                    R.drawable.map_killing_fields
            };

    private static final double[][] level_placeable =
            {
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0},
                    {366.0/681.0,615.0/681.0}
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_room);

        BaseObject.sSystemRegistry.systemUiManager.changeDecorView(getWindow().getDecorView());
        BaseObject.sSystemRegistry.systemUiManager.hideSystemUIALL();
//        Objects.requireNonNull(getSupportActionBar()).setTitle("");

//        soundManager = new SoundManager();

        level_show = (ImageView)findViewById(R.id.IV_selected_level);
        levels = (Gallery)findViewById(R.id.level_selection);
        play = (Button)findViewById(R.id.level_selection_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(BaseObject.sSystemRegistry.contextParameters.context, EditBattleHand.class);
                    intent.putExtra("BitmapImage", level_images_ids[map_selected]);
                    intent.putExtra("songId",level_songs[map_selected]);
                    intent.putExtra("TopEdge",level_placeable[map_selected][0]);
                    intent.putExtra("BottomEdge",level_placeable[map_selected][1]);
                    startActivityForResult(intent,1);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    DebugLog.d("Selection Room",e.toString());
                }

            }
        });
        level_name_TV = (TextView)findViewById(R.id.select_map_name_tv);
        backButton = (ImageButton)findViewById(R.id.selection_room_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseObject.sSystemRegistry.soundManager.stopMusic();
                finish();
            }
        });

        Integer[] levels_images = {
                R.drawable.icon_map_killing_fields,
                R.drawable.icon_map_island,
                R.drawable.icon_map_island_night,
                R.drawable.icon_map_over_pass,
                R.drawable.icon_map_over_pass_night,
                R.drawable.icon_map_volcano_pass,
                R.drawable.icon_map_mountain_pass,
                R.drawable.icon_map_tundra,
                R.drawable.icon_map_tundra_night,
                R.drawable.icon_map_frozen_forest,
                R.drawable.icon_map_forest,
                R.drawable.icon_map_forest_night,
        };
        levels.setAdapter(new LevelGalleryAdapter(BaseObject.sSystemRegistry.contextParameters.context,0,levels_images));
        levels.setSpacing(10);
        levels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                level_show.setImageResource((Integer)parent.getItemAtPosition(position));
                level_show.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                level_name_TV.setText(level_names[position]);
                map_selected = position;
            }
        });


        BaseObject.sSystemRegistry.soundManager.loadMusic(SoundManager.SELECTION_MENU);
        BaseObject.sSystemRegistry.soundManager.playMusic();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BaseObject.sSystemRegistry.systemUiManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            BaseObject.sSystemRegistry.systemUiManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
        } else {
            BaseObject.sSystemRegistry.systemUiManager.removeMessages(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
}