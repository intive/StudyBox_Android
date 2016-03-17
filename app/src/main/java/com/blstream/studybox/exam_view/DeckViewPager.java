package com.blstream.studybox.exam_view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DeckViewPager extends ViewPager {

    public DeckViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float mStartDragX;
    int startItem;
    float distanceX;
    float lastDistanceX;
    boolean multiPtrs = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isScrollEnabled(event) && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isScrollEnabled(event) && super.onInterceptTouchEvent(event);
    }

    public boolean isScrollEnabled(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartDragX = x;
                startItem = getCurrentItem();
                break;
            case MotionEvent.ACTION_MOVE:
                return canScroll(event, x);
        }
        return true;
    }

    public boolean canScroll(MotionEvent event, float x){
        int pointerCount = event.getPointerCount();
        if (pointerCount == 1) {
            return WereMutliPointers(event) || canPageScroll(event, x);
        } else if (pointerCount > 1) {
            multiPtrs = true;
            return false;  //Disable scroll for multi-pointers
        }
        return true;
    }

    public boolean WereMutliPointers(MotionEvent event) {
        if (multiPtrs) {
            //Set ACTION_UP for last pointer from multi-pointers
            event.setAction(MotionEvent.ACTION_UP);
            multiPtrs = false;
            return true;
        }
        return false;
    }

    public boolean canPageScroll(MotionEvent event, float x) {
        lastDistanceX = distanceX;
        distanceX = x - mStartDragX;

        if (mStartDragX < x && startItem % 2 == 0)
            return false; //Disable left scroll on even page when tapping
        else if (mStartDragX > x && startItem % 2 == 1)
            return false; //Disable right scroll on odd page when tapping
        else if (x < mStartDragX && startItem % 2 == 0)
            checkRightMotionDirection(event);
        else if (x > mStartDragX && startItem % 2 == 1)
            checkLeftMotionDirection(event);

        return true;
    }

    public void checkRightMotionDirection(MotionEvent event){
        //Set current page when dragging right even page
        if (lastDistanceX < distanceX)
            event.setAction(MotionEvent.ACTION_UP);
    }

    public void checkLeftMotionDirection(MotionEvent event){
        //Set current page when dragging left odd page
        if (lastDistanceX > distanceX)
            event.setAction(MotionEvent.ACTION_UP);
    }
}

