package com.mememe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.mememe.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemesengerActivity extends Activity {
    User user;

    private CircleImageView circleImageView;

    private PaintView paintView;

    private Button setColorButton, setSizeButton, setBackgroundButton;
    DisplayMetrics metrics;
    SeekBar seekBarFont;

    int[] COLORS = {Color.BLUE, Color.CYAN, Color.DKGRAY, Color.BLACK, Color.LTGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW};
    int BRUSH_COLOR = Color.BLACK;
    int BRUSH_SIZE = 5;

    int COLOR_INDEX = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_memesenger);

        setColorButton = findViewById(R.id.brush_color);
        setSizeButton = findViewById(R.id.brush_size);
        setBackgroundButton = findViewById(R.id.background_color);

        circleImageView = findViewById(R.id.profile_image);
        seekBarFont = findViewById(R.id.seekbar_font);

        user = getIntent().getParcelableExtra("user");
        checkUser();

        paintView = findViewById(R.id.paintView);
        try{
            metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

//            paintView.invalidate();
            paintView.init(metrics,BRUSH_COLOR,BRUSH_SIZE);
            paintView.normal();
        }catch (Exception e){
            Log.e(">> GG", "GG" +e);
        }

        seekBarFont.setVisibility(View.GONE);
    }

    public void profileOnClick(View v){
        logout();
    }

    public void checkUser(){
        Toast.makeText(MemesengerActivity.this,"USER: "+user.getName(),Toast.LENGTH_LONG).show();
        if (!user.getId().equals("")){
            Glide.with(MemesengerActivity.this).load(user.getImage_url()).into(circleImageView);
        }
    }

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MemesengerActivity.this);
        builder.setMessage("CONFIRM LOGOUT?");

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(MemesengerActivity.this,MainActivity.class);
                intent.putExtra("status","logout");
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setColor(View view) {

        Log.i(">> COLOR INDEX: ",""+COLOR_INDEX);
        seekBarFont.setProgress(COLOR_INDEX);
        paintView.init(metrics,COLORS[COLOR_INDEX],BRUSH_SIZE);
        Shader shader = new LinearGradient(0, 0, 800.f, 0.0f, COLORS,null, Shader.TileMode.CLAMP);

        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setShader(shader);

        seekBarFont.setVisibility(View.VISIBLE);
        seekBarFont.setProgressDrawable(shape);

        seekBarFont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int index = COLOR_INDEX;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                index = seekBar.getProgress();
//                BRUSH_COLOR = COLORS[index];
//                paintView.init(metrics,BRUSH_COLOR,BRUSH_SIZE);

                Log.i(">> COLOR INDEX",""+progress  );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                index = seekBar.getProgress();
                BRUSH_COLOR = COLORS[index];
                paintView.init(metrics,BRUSH_COLOR,BRUSH_SIZE);
                Log.i(">> COLOR INDEX",""+index);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int index = seekBar.getProgress();
                try {
                    BRUSH_COLOR = COLORS[index];
                    paintView.init(metrics,BRUSH_COLOR,BRUSH_SIZE);

                }catch (Exception e){
                    Log.e(">> SET COLOR",""+e);
                }
                COLOR_INDEX = index;
            }
        }
        );
    }

    public void setSize(View view) {
        Log.i(">> C Index",""+COLOR_INDEX);
        Shader shader = new LinearGradient(0, 0, 800.f, 0.0f, Color.WHITE,Color.BLACK, Shader.TileMode.CLAMP);

        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setShader(shader);

        seekBarFont.setVisibility(View.VISIBLE);
        seekBarFont.setProgressDrawable(shape);
        seekBarFont.setMax(30);

        seekBarFont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                BRUSH_SIZE = seekBar.getProgress();
                paintView.init(metrics,BRUSH_COLOR,BRUSH_SIZE);
            }
        });
    }
}