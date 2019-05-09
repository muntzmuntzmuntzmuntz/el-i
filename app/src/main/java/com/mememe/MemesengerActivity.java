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
import com.mememe.processors.Brush;
import com.mememe.processors.Brush_Size;
import com.mememe.processors.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemesengerActivity extends Activity {
    Brush brush;

    User user;

    private CircleImageView circleImageView;

    private PaintView paintView;

    private Button setColorButton, setSizeButton, setBackgroundButton;
    DisplayMetrics metrics;
    SeekBar seekBarFont;

    int[] COLORS = {Color.BLUE, Color.CYAN, Color.DKGRAY, Color.BLACK, Color.LTGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW};
    int BRUSH_COLOR = 3;
    int BRUSH_SIZE = 5;

    int COLOR_INDEX = 3;
    int SIZE_INDEX = 5;

    Boolean colorButtonClicked = false;
    Boolean sizeButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_memepaint);

        setColorButton = findViewById(R.id.brush_color);
        setSizeButton = findViewById(R.id.brush_size);
        setBackgroundButton = findViewById(R.id.background_color);

        circleImageView = findViewById(R.id.profile_image);
        seekBarFont = findViewById(R.id.seekbar_font);

        brush = new Brush(seekBarFont);

//        user = getIntent().getParcelableExtra("user");
//        checkUser();

        paintView = findViewById(R.id.paintView);
        try{
            metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            paintView.init(metrics,COLORS[BRUSH_COLOR],BRUSH_SIZE);
            paintView.normal();
        }catch (Exception e){
            Log.e(">> GG", "GG" +e);
        }

        seekBarFont.setVisibility(View.GONE);
    }

    public void profileOnClick(View v){
        Toast.makeText(MemesengerActivity.this,"USER: GGG",Toast.LENGTH_LONG).show();
        new Profile().showPopup();
//        logout();
    }

    public void checkUser(){
        new Profile().showPopup();
//        Toast.makeText(MemesengerActivity.this,"USER: GGG",Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(MemesengerActivity.this,MainActivity.class);
//        Toast.makeText(MemesengerActivity.this,"USER: "+user.getName(),Toast.LENGTH_LONG).show();
//        if (!user.getId().equals("")){
//            Glide.with(MemesengerActivity.this).load(user.getImage_url()).into(circleImageView);
//        }else {
//            Toast.makeText(MemesengerActivity.this,"USER: "+user.getName(),Toast.LENGTH_LONG).show();
//
////            Glide.with(MemesengerActivity.this).load("N").into(circleImageView);
//        }
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

    public void showAccountInfo(){

    }

    public void setColor(View view) {
        sizeButtonClicked = false;
        if (colorButtonClicked) {
            seekBarFont.setVisibility(View.GONE);
            colorButtonClicked = false;
        }
        else {
            seekBarFont.setVisibility(View.VISIBLE);
            colorButtonClicked = true;

            seekBarFont.setMax(10);

            Log.i(">> COLOR INDEX: ", "" + COLOR_INDEX);
            seekBarFont.setProgress(COLOR_INDEX);
            paintView.init(metrics, COLORS[COLOR_INDEX], BRUSH_SIZE);
            Shader shader = new LinearGradient(0, 0, 800.f, 0.0f, COLORS, null, Shader.TileMode.CLAMP);

            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            shape.getPaint().setShader(shader);

            seekBarFont.setProgressDrawable(shape);

            seekBarFont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int index = seekBar.getProgress();
                    try {
                        BRUSH_COLOR = COLORS[index];
                        paintView.init(metrics, BRUSH_COLOR, BRUSH_SIZE);
                        setColorButton.setTextColor(BRUSH_COLOR);
                        Log.i(">> SET COLOR", "INDEX: " + index);
                        COLOR_INDEX = index;
                    } catch (Exception e) {
                        Log.e(">> SET COLOR", "INDEX: " + index + " Error :" + e);
                    }
                }
            });
        }
        paintView.setEnabled(true);
    }

    public void setSize(View view) {
        colorButtonClicked = false;
        if (sizeButtonClicked) {
            seekBarFont.setVisibility(View.GONE);
            sizeButtonClicked = false;

        }
        else {
            BRUSH_SIZE = brush.getSize();
            sizeButtonClicked = true;

            paintView.init(metrics, COLORS[COLOR_INDEX],BRUSH_SIZE);

//            seekBarFont.setVisibility(View.VISIBLE);
//            sizeButtonClicked = true;
//
//            seekBarFont.setMax(30);
//
//            seekBarFont.setProgress(SIZE_INDEX);
//
//            Shader shader = new LinearGradient(0, 0, 800.f, 0.0f, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);
//
//            ShapeDrawable shape = new ShapeDrawable(new RectShape());
//            shape.getPaint().setShader(shader);
//
//            seekBarFont.setProgressDrawable(shape);
//
//            seekBarFont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//                    BRUSH_SIZE = seekBar.getProgress();
//                    try {
//                        paintView.init(metrics, COLORS[COLOR_INDEX], BRUSH_SIZE);
//                        Log.i(">> SIZE:", "" +BRUSH_SIZE);
//                        SIZE_INDEX = BRUSH_SIZE;
//                    } catch (Exception e) {
//                        Log.e(">> SET SIZE:", "" + e);
//                    }
//                }
//            });
        }
        paintView.setEnabled(true);
    }


}