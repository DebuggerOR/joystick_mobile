package com.example.javaapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoyStickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private float centerX, centerY, baseRadius, hatRadius;
    private JoyStickListner joyStickCallback;
    private final int ratio = 5;

    private void setupDimensions() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = Math.min(getWidth(), getHeight()) / 5;
    }

    public JoyStickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoyStickListner) {
            joyStickCallback = (JoyStickListner) context;
        }
    }

    public JoyStickView(Context context, AttributeSet attributes) {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoyStickListner) {
            joyStickCallback = (JoyStickListner) context;
        }
    }

    public JoyStickView(Context context, AttributeSet attributes, int style) {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoyStickListner) {
            joyStickCallback = (JoyStickListner) context;
        }
    }

    private void drawJoyStick(float newX, float newY) {
        if (getHolder().getSurface().isValid()) {
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            colors.setARGB(255, 50, 50, 50);
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors);
            colors.setARGB(255, 0, 0, 255);
            myCanvas.drawCircle(newX, newY, hatRadius, colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions();
        drawJoyStick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.equals(this)) {
            if (motionEvent.getAction() != motionEvent.ACTION_UP) {
                float displacement = (float) Math.sqrt((Math.pow(motionEvent.getX() - centerX, 2)
                        + Math.pow(motionEvent.getY() - centerY, 2)));
                if (displacement < baseRadius) {
                    drawJoyStick(motionEvent.getX(), motionEvent.getY());
                    joyStickCallback.onJoyStickMoved((motionEvent.getX()-centerX)/baseRadius,
                            (motionEvent.getY()-centerY)/baseRadius,getId());
                } else {
                    float ratio = baseRadius / displacement;
                    float constX = centerX + (motionEvent.getX() - centerX) * ratio;
                    float constY = centerY + (motionEvent.getY() - centerY) * ratio;
                    drawJoyStick(constX, constY);
                    joyStickCallback.onJoyStickMoved((constX-centerX)/baseRadius,
                            (constY-centerY)/baseRadius,getId());
                }
            } else {
                drawJoyStick(centerX, centerY);
                joyStickCallback.onJoyStickMoved(0,0,getId());
            }
        }
        return true;
    }

    public interface JoyStickListner {
        void onJoyStickMoved(float xPercent, float yPercent, int id);
    }
}
