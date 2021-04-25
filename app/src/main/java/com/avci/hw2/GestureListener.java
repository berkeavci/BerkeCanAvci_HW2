package com.avci.hw2;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private Context context;

    public GestureListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Toast.makeText(context, "onSingleTapConfirmed Over Image", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return super.onSingleTapConfirmed(e);
    }

//    public interface GestureListenerInteface{
//        boolean onLongPress(MotionEvent e);
//        boolean onDoubleTap(MotionEvent e);
//    }
}
