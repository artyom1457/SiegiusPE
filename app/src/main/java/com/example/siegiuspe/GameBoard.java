package com.example.siegiuspe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siegiuspe.Activities.MainActivity;
import com.example.siegiuspe.Game.Game;
import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.Managers.SystemUiManager;

public class GameBoard extends AppCompatActivity  {

    private TextView moneyTV;
    private ImageView troop1,troop2,troop3,troop4,troop5,hero;

    private Game game;

    private ImageButton move_left, move_right;
    protected Bitmap background;

    private int commander;
    private int songId;
    private int money = 0;
    private int economy = 0;
    private boolean keepMoveLeft;
    private boolean keepMoveRight;

    private Thread move_thread;
    private Thread MoneyGenerator;
    private boolean thread_running;

    private Handler gameHandler;

    private int topEdge;
    private int bottomEdge;
    private double topOffset;
    private double bottomOffset;

    private Button back;
    private TextView textView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        BaseObject.sSystemRegistry.systemUiManager.changeDecorView(getWindow().getDecorView());
        BaseObject.sSystemRegistry.systemUiManager.hideSystemUIALL();

        moneyTV = (TextView)findViewById(R.id.hud_money);
        troop1 = (ImageView)findViewById(R.id.hud_troop1);
        troop2 = (ImageView)findViewById(R.id.hud_troop2);
        troop3 = (ImageView)findViewById(R.id.hud_troop3);
        troop4 = (ImageView)findViewById(R.id.hud_troop4);
        troop5 = (ImageView)findViewById(R.id.hud_troop5);
        hero = (ImageView)findViewById(R.id.hud_hero);


        troop1.setOnLongClickListener(new MyLongClickListener());
        troop2.setOnLongClickListener(new MyLongClickListener());
        troop3.setOnLongClickListener(new MyLongClickListener());
        troop4.setOnLongClickListener(new MyLongClickListener());
        troop5.setOnLongClickListener(new MyLongClickListener());
        hero.setOnLongClickListener(new MyLongClickListener());


        keepMoveLeft = false;
        keepMoveRight = false;
        thread_running = true;

        move_left = (ImageButton)findViewById(R.id.move_left_button);
        move_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        keepMoveLeft = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        keepMoveLeft = false;
                        break;
                }
                return true;
            }
        });

        move_right = (ImageButton)findViewById(R.id.move_right_button);
        move_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        keepMoveRight = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        keepMoveRight = false;
                        break;
                }
                return true;
            }
        });

        move_thread = new Thread()
        {
            public void run() {
                while (thread_running)
                {
                    if(keepMoveLeft && !keepMoveRight)
                    {
                        game.moveLeft();
                    }
                    if(keepMoveRight && !keepMoveLeft)
                    {
                        game.moveRight();
                    }
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        move_thread.start();

        MoneyGenerator = new Thread(){
            public void run()
            {
                while (thread_running)
                {
                    if( gameHandler != null) {
                        money += economy;
                        Message msg = new Message();
                        msg.arg1 = 2;
                        gameHandler.sendMessage(msg);
                        try {
                            sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        MoneyGenerator.start();


        Intent intent = getIntent();
        int id = intent.getExtras().getInt("BitmapImage",R.drawable.map_killing_fields);
        songId = intent.getExtras().getInt("songId",R.raw.music_army);
        topOffset = intent.getExtras().getDouble("TopEdge",500.0/681.0);
        bottomOffset = intent.getExtras().getDouble("BottomEdge",641.0/681.0);
        money = intent.getExtras().getInt("Money",0);
        economy = intent.getExtras().getInt("Economy",250);
        background = BitmapFactory.decodeResource(getResources(), id);
        loadHud(intent);

        gameHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.arg1)
                {
                    case 1:
                        createEndGameDialog(msg.arg2);
                        break;
                    case 2:
                        moneyTV.setText(String.valueOf(money));
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(game == null)
        {
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_game);

            int width = linearLayout.getWidth();
            int height = linearLayout.getHeight();
            float scale = (float)height/background.getHeight();
            int gameWidth = (int)(background.getWidth() * scale * 1.2);

            topEdge = (int)(height*topOffset);
            bottomEdge = (int)(height*bottomOffset);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size);

            game = new RealGame(background,topEdge,bottomEdge,gameHandler,commander,songId);
            game.bootstrap(this,gameWidth,height,size.x,size.y);
            linearLayout.addView(game.getRenderer());
            linearLayout.setOnDragListener(new MyDragEventListener());
        }

        if (hasFocus) {
            BaseObject.sSystemRegistry.systemUiManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
        } else {
            BaseObject.sSystemRegistry.systemUiManager.removeMessages(0);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BaseObject.sSystemRegistry.systemUiManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        thread_running = false;
        game.stopGame();
        game = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(game!=null) {
            game.resumeGame();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();
        // Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show();
        if(game!=null) {
            game.pauseGame();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Toast.makeText(this,"ondestroy",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit the Game?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BaseObject.sSystemRegistry.contextParameters.context,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private boolean placeable(int y)
    {
        return (y >= topEdge && y <= bottomEdge);
    }


    public void createEndGameDialog(int hp)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View endView = getLayoutInflater().inflate(R.layout.pop_up_won,null);
        back = (Button)endView.findViewById(R.id.end_battle_back_button);
        textView = (TextView)endView.findViewById(R.id.end_battle_text);

        if(hp > 0)
        {
            textView.setText("Defeat");
            textView.setTextColor(Color.rgb(255,102,102));
        }
        else
        {
            textView.setText("Victory");
            textView.setTextColor(Color.rgb(205,165,91));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseObject.sSystemRegistry.contextParameters.context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialogBuilder.setView(endView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }


    private class MyDragEventListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            final int action = event.getAction();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    game.startDrawEdges();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    return true;

                case DragEvent.ACTION_DROP:
                    ImageView view = (ImageView)event.getLocalState();
                    if(placeable((int)event.getY())) {
                        int id = Integer.parseInt(view.getContentDescription().toString());
                        int cost = Utils.getTroopCostById(id);
                        if(cost < money)
                        {
                            money -= cost;
                            moneyTV.setText(String.valueOf(money));
                            game.addToGame(Utils.getTroopById(id, true, ContextParameters.bgX, (int) (event.getY())));
                        }
                        else
                        {
                            Toast.makeText(BaseObject.sSystemRegistry.contextParameters.context,"Not enough money!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    game.endDrawEdges();
                    return true;
                default:
                    DebugLog.d("drag event","unknown event");
                    break;
            }

            return true;
        }
    }

    private void loadHud(Intent intent)
    {
        commander = intent.getExtras().getInt("commander",Utils.CAESAR);
        int troop1_id,troop2_id,troop3_id,troop4_id,troop5_id;
        int troopDef_id;

        if(commander == Utils.CAESAR) {
            hero.setContentDescription(Integer.toString(Utils.CAESAR));
            hero.setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(Utils.CAESAR)));
            troopDef_id = Utils.LEGIONARY;
        }
        else
        {
            hero.setContentDescription(Integer.toString(Utils.VERCING));
            hero.setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(Utils.VERCING)));
            troopDef_id = Utils.RAIDER;
        }

        troop1_id = intent.getExtras().getInt("hud_troop1", troopDef_id);
        troop2_id = intent.getExtras().getInt("hud_troop2", troopDef_id);
        troop3_id = intent.getExtras().getInt("hud_troop3", troopDef_id);
        troop4_id = intent.getExtras().getInt("hud_troop4", troopDef_id);
        troop5_id = intent.getExtras().getInt("hud_troop5", troopDef_id);

        troop1.setContentDescription(Integer.toString(troop1_id));
        troop2.setContentDescription(Integer.toString(troop2_id));
        troop3.setContentDescription(Integer.toString(troop3_id));
        troop4.setContentDescription(Integer.toString(troop4_id));
        troop5.setContentDescription(Integer.toString(troop5_id));

        troop1.setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(troop1_id)));
        troop2.setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(troop2_id)));
        troop3.setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(troop3_id)));
        troop4.setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(troop4_id)));
        troop5.setBackground(new BitmapDrawable(getResources(), Utils.getTroopBitmapById(troop5_id)));

        moneyTV.setText(String.valueOf(money));
    }


    private class MyLongClickListener implements View.OnLongClickListener
    {
        @Override
        public boolean onLongClick(View v) {

            ClipData.Item item = new ClipData.Item("troop dropped");
            ClipData dragData = new ClipData(
                    (CharSequence) v.getTag(),
                    new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN},item);


            double[] offsets = Utils.getOffsetsByID(Integer.parseInt(v.getContentDescription().toString()));
            View.DragShadowBuilder myShadow = new MyDragShadowBuilder(v, Utils.getTroopBitmapById(Integer.parseInt(v.getContentDescription().toString())),(float)offsets[0],(float)offsets[1]);
            v.startDragAndDrop(dragData,myShadow,v,0);
            return true;
        }
    }
}