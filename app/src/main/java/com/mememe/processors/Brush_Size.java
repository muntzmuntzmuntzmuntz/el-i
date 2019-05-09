package com.mememe.processors;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class Brush_Size {

    SeekBar seekBarFont;
    int SIZE_INDEX;

    public Brush_Size(SeekBar seekBar){
        seekBarFont = seekBar;
    }

    public void getIndex(){
        seekBarFont.setVisibility(View.VISIBLE);
//        sizeButtonClicked = true;

        seekBarFont.setMax(30);

        seekBarFont.setProgress(SIZE_INDEX);

        Shader shader = new LinearGradient(0, 0, 800.f, 0.0f, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);

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
                SIZE_INDEX = seekBar.getProgress();
//                BRUSH_SIZE = seekBar.getProgress();
//                try {
//                    paintView.init(metrics, COLORS[COLOR_INDEX], BRUSH_SIZE);
//                    Log.i(">> SIZE:", "" +BRUSH_SIZE);
//                    SIZE_INDEX = BRUSH_SIZE;
//                } catch (Exception e) {
//                    Log.e(">> SET SIZE:", "" + e);
//                }
            }
        });
    }
}
