package com.blstream.studybox.exam_view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.squareup.picasso.Picasso;

public class ImageTextDisplay {

    private final int preloadImageCount;
    private int prevIndex;
    private int index;
    private final int height;
    private final int width;
    private final Context context;
    private final Activity activity;

    public ImageTextDisplay(int preloadImageCount, Activity activity) {
        this.preloadImageCount = preloadImageCount;
        this.activity = activity;
        this.context = activity.getBaseContext();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    public ImageView[] init(FrameLayout frameLayout, TextView tvQuestion, String[] imgToLoad){
        ImageView[] imageTab = setImageTab(frameLayout);
        initPreloadImages(imgToLoad, imageTab);
        setView(imageTab[0], tvQuestion, imgToLoad[0]);
        return imageTab;
    }

    public void initOnRestart(ImageView[] imageTab, TextView tvQuestion, String[] imgToLoad ){
        initPreloadImages(imgToLoad, imageTab);
        setView(imageTab[0], tvQuestion, imgToLoad[0]);
    }

    private ImageView[] setImageTab(FrameLayout frameLayout){
        ImageView[] imageTab = new ImageView[preloadImageCount];
        for (int i = 0; i < preloadImageCount; i++) {
            imageTab[i] = new ImageView(context);
            frameLayout.addView(imageTab[i]);
            imageTab[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        return imageTab;
    }

    private void setView(ImageView image, TextView tv, String textToDisplay){
        String text = "";
        if (Patterns.WEB_URL.matcher(textToDisplay).matches()) {
            image.setVisibility(View.VISIBLE);
        } else {
            text = textToDisplay;
        }
        tv.setText(text);
    }

    public void initPreloadImages(String[] imgToLoad, ImageView[] imageTab){
        for (int i = 0; i < preloadImageCount; i++) {
            imageTab[i].setVisibility(View.INVISIBLE);
            preloadImage(imgToLoad[i], imageTab[i]);
        }
    }

    public void changeData(String currentData, String dataToPreload,
                           TextView tv, ImageView[] imageTab) {
        if (Patterns.WEB_URL.matcher(currentData).matches()) {
            showImage(tv, imageTab);
        } else {
            showText(currentData, tv, imageTab);
        }
        preloadImage(dataToPreload, imageTab[prevIndex]);
    }

    public void setImgIndexes(int position){
        index = (position) % preloadImageCount;
        prevIndex = (position - 1) % preloadImageCount;
    }

    private void showText(String currentData, TextView tv, ImageView[] imageTab){
        tv.setText(currentData);
        imageTab[prevIndex].setVisibility(View.INVISIBLE);
    }

    private void showImage(TextView tv, ImageView[] imageTab){
        tv.setText("");
        imageTab[index].setVisibility(View.VISIBLE);
        imageTab[prevIndex].setVisibility(View.INVISIBLE);
    }

    private void preloadImage(String dataToPreload, ImageView image){
        if (dataToPreload == null) {
            return;
        }
        if (Patterns.WEB_URL.matcher(dataToPreload).matches()) {
            Picasso.with(activity).load(dataToPreload).resize(width, height).centerInside()
                    .placeholder(R.drawable.camera).into(image);
        }
    }
}
