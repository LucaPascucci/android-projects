package it.lucapascucci.materialdesing.views;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Luca on 14/03/15.
 */
public class PascuLayout extends FrameLayout{


    public static final String TAG = "PASCU";
    Paint paint = null;

    public PascuLayout (Context context){
        super(context);
        init();
    }

    public PascuLayout(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }

    public PascuLayout(Context context,AttributeSet attributeSet, int defStyleAttr){
        super(context,attributeSet,defStyleAttr);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        setWillNotDraw(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()){

            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "PascuLayout dispatchTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "PascuLayout dispatchTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "PascuLayout dispatchTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "PascuLayout dispatchTouchEvent CANCEL");
                break;
        }
        boolean b = super.dispatchTouchEvent(ev);
        Log.d(TAG,"PascuLayout dispatchTouchEvent RETURNS "+b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()){

            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "PascuLayout onInterceptTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "PascuLayout onInterceptTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "PascuLayout onInterceptTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "PascuLayout onInterceptTouchEvent CANCEL");
                break;
        }
        boolean b = super.dispatchTouchEvent(ev);
        Log.d(TAG,"PascuLayout onInterceptTouchEvent RETURNS "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "PascuLayout onTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "PascuLayout onTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "PascuLayout onTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "PascuLayout onTouchEvent CANCEL");
                break;
        }
        boolean b=super.onTouchEvent(event);
        Log.d(TAG,"PascuLayout onTouchEvent RETURNS "+b);
        return b;
    }
}
