package com.example.mysensorapp.mysensorapp;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private SparseArray<PointF> pointsList;

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointsList = new SparseArray<PointF>();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.mDetector.onTouchEvent(event);

        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers

                PointF pointF = new PointF();

                pointF.x = event.getX(pointerIndex);
                pointF.y = event.getY(pointerIndex);
                pointsList.put(pointerId, pointF);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF pointF = pointsList.get(event.getPointerId(i));
                    if(pointF != null) {
                        pointF.x = event.getX(i);
                        pointF.y = event.getY(i);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                pointsList.remove(pointerId);
                break;
            }
        }

        for(int size = pointsList.size(), i = 0; i < size; i++) {
            PointF point = pointsList.valueAt(i);
            if (point != null) {
                //Log.i(" On Touch Event: ", "X: " + point.x + ", Y: " + point.y);
            }
        }
        return true;
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
            return true;
        }
    }
}
