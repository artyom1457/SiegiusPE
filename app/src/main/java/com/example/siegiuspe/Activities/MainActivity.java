package com.example.siegiuspe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.Game.Game;
import com.example.siegiuspe.Game.IntroDemoGame;
import com.example.siegiuspe.OptionsFragment;
import com.example.siegiuspe.R;
import com.example.siegiuspe.Utils;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long backPressedTime; // for knowing when the user pressed back again
    private Toast backToast; // back message
    private Button startButton;
    private ImageView logo;
    private Game game;

    private AlertDialog alertDialog;
    private EditText et_nickname, et_password, et_email;
    private Button bt_login ,bt_Register, bt_create;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        BaseObject.sSystemRegistry.systemUiManager.changeDecorView(getWindow().getDecorView());
//        BaseObject.sSystemRegistry.systemUiManager.hideSystemUINavigation();


        startButton = (Button)findViewById(R.id.BTplay);
        startButton.setOnClickListener(this);
        logo = (ImageView)findViewById(R.id.IVlogo);

        Utils.dmgPaint.setARGB(255,255,0,0);
        Utils.dmgPaint.setTextSize(30);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int width = size.x;
        int height = size.y;

        game = new IntroDemoGame();
        game.bootstrap(this,(int)(width*1.2),height,width,height);

        linearLayout = (LinearLayout)findViewById(R.id.llIntro);
        linearLayout.addView(game.getRenderer());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        BaseObject.sSystemRegistry.systemUiManager.delayedHideNavigation(SystemUiManager.INITIAL_HIDE_DELAY);
//        mSysManager.delayedHideNavigation(SystemUiManager.INITIAL_HIDE_DELAY);

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

//        if (hasFocus) {
//            BaseObject.sSystemRegistry.systemUiManager.delayedHideNavigation(SystemUiManager.INITIAL_HIDE_DELAY);
////            mSysManager.delayedHideNavigation(SystemUiManager.INITIAL_HIDE_DELAY);
//        } else {
//            BaseObject.sSystemRegistry.systemUiManager.removeMessages(1);
////            mSysManager.removeMessages(1);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);

        for(int i=0;i<menu.size();i++)
        {
            MenuItem item= menu.getItem(i);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }


    @Override
    public void onClick(View v) {

        if(v == startButton) {
            Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.move_up_shrink);
            logo.startAnimation(mAnimation);
            mAnimation = AnimationUtils.loadAnimation(this, R.anim.button_disappear);
            startButton.startAnimation(mAnimation);
            startButton.setClickable(false);
            startButton.setVisibility(View.INVISIBLE);
            openOptionsFragment();
        }
    }

    public void openOptionsFragment()
    {
        OptionsFragment fragment = OptionsFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.oprion_right,R.anim.oprion_left);
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container,fragment,"OPTION_FRAGMENT").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu1) {
            settingsActivity();
            return true;
        }
        else if (id == R.id.action_menu2) {
            createNewLoginDialog();
            return true;
        }
        else if (id == R.id.action_menu3) {
            statisticsActivity();
            return true;
        }
        else if (id == R.id.action_menu4) {
            return true;
        }
        return true;
    }

    private void settingsActivity()
    {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivityForResult(intent,1);
    }

    private void statisticsActivity()
    {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(game!=null) {
            game.resumeGame();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(game == null)
        {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size);
            int width = size.x;
            int height = size.y;
            ContextParameters.bgX = -555;
            ContextParameters.bgY = 0;
            game = new IntroDemoGame();
            game.bootstrap(this,(int)(width*1.2),height,width,height);
            linearLayout.addView(game.getRenderer());
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
    protected void onStop() {
        super.onStop();
        // Toast.makeText(this,"onstop",Toast.LENGTH_LONG).show();
        game.stopGame();
        game = null;
        linearLayout.removeAllViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Toast.makeText(this,"ondestroy",Toast.LENGTH_LONG).show();
    }

    public void createNewLoginDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View loginView = getLayoutInflater().inflate(R.layout.popup_login,null);
        et_nickname = (EditText)loginView.findViewById(R.id.etNickname_login);
        et_password = (EditText)loginView.findViewById(R.id.etPassword_login);
        bt_login = (Button)loginView.findViewById(R.id.btLogin);
        bt_Register = (Button)loginView.findViewById(R.id.btRegister);

        bt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                createNewRegisterDialog();
            }
        });

        dialogBuilder.setView(loginView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    public void createNewRegisterDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View loginView = getLayoutInflater().inflate(R.layout.popup_register,null);
        et_nickname = (EditText)loginView.findViewById(R.id.etNickname_register);
        et_email = (EditText)loginView.findViewById(R.id.etEmail_register);
        et_password = (EditText)loginView.findViewById(R.id.etPassword_register);
        bt_create = (Button)loginView.findViewById(R.id.btCreate);

        dialogBuilder.setView(loginView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}