package it.lucapascucci.materialdesing.views;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Luca on 14/03/15.
 */
public class PascuView extends TextView {

    public static final String TAG = "PASCU";
    Paint paint = null;

    public PascuView(Context context) {
        super(context);
        init();
    }

    public PascuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PascuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "View dispatchTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "View dispatchTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "View dispatchTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "View dispatchTouchEvent CANCEL");
                break;
        }
        boolean b=super.dispatchTouchEvent(event);
        Log.d(TAG,"View dispatchTouchEvent RETURNS "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "View onTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "View onTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "View onTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "View onTouchEvent CANCEL");
                break;
        }
        boolean b=super.onTouchEvent(event);
        Log.d(TAG,"View onTouchEvent RETURNS "+b);
        return b;
    }


    public void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
    }
}
