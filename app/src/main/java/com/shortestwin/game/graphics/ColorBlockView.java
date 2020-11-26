package com.shortestwin.game.graphics;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class ColorBlockView extends View {

    private Context context;

    private Paint paint;


    public ColorBlockView(Context context) {
        super(context);
        this.init(context);
    }

    public ColorBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public ColorBlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.paint = new Paint();

        this.paint.setColor(Color.CYAN);
        this.paint.setStyle(Paint.Style.FILL);
    }

    public void setColor(int color) {
        this.paint.setColor(color);

        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final ColorBlockView classInstance = this;

        ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(Color.BLACK)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        classInstance.setColor(selectedColor);
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();

        return super.onTouchEvent(event);
    }
}
