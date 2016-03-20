package com.blstream.studybox.exam_view;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.squareup.picasso.Picasso;

public class ImageTextDisplayer {

    private int preloadImageCount;
    private int prevIndex;
    private int index;
    private Context context;
    Activity activity;

    public ImageTextDisplayer(int preloadImageCount) {
        this.preloadImageCount = preloadImageCount;
    }

    public void initVariables(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public ImageView[] setImageTab(FrameLayout frameLayout){
        ImageView[] imageTab = new ImageView[preloadImageCount];
        for (int i = 0; i < preloadImageCount; i++) {
            imageTab[i] = new ImageView(context);
            frameLayout.addView(imageTab[i]);
            imageTab[i].setVisibility(View.INVISIBLE);
            imageTab[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        return imageTab;
    }

    public void setView(ImageView image, TextView tv, String textToDisplay){
        if (Patterns.WEB_URL.matcher(textToDisplay).matches()) {
            tv.setVisibility(View.INVISIBLE);
            image.setVisibility(View.VISIBLE);
            Picasso.with(activity).load(textToDisplay).fit().centerInside()
                    .placeholder(R.drawable.camera).into(image);
        } else {
            tv.setText(textToDisplay);
            tv.setVisibility(View.VISIBLE);
            image.setVisibility(View.INVISIBLE);
        }
    }

    public void initPreloadImages(String[] answer, ImageView[] imageTab){
        for(int i = 1; i < preloadImageCount; i++){
            loadImage(answer[i], imageTab[i]);
        }
    }

    public void changeData(String currentData, String dataToPreload, TextView tv, ImageView[] imageTab) {
        if (Patterns.WEB_URL.matcher(currentData).matches())
            showImage(dataToPreload, tv, imageTab);
        else
            showText(dataToPreload, currentData, tv, imageTab);
    }

    public void setImgIndexes(int index){
        this.index = index;
        prevIndex = index - 1;
        if(prevIndex == -1)
            prevIndex = preloadImageCount - 1;
    }

    public void showText(String dataToPreload, String currentData, TextView tv, ImageView[] imageTab){
        tv.setText(currentData);
        tv.setVisibility(View.VISIBLE);

        for(int i = 0; i < preloadImageCount; i++){
            imageTab[i].setVisibility(View.INVISIBLE);
            if(index == i){
                loadImage(dataToPreload, imageTab[prevIndex]);
            }
        }
    }

    public void showImage(String dataToPreload, TextView tv, ImageView[] imageTab){
        tv.setVisibility(View.INVISIBLE);
        for(int i = 0; i < preloadImageCount; i++){
            if(index == i){
                imageTab[i].setVisibility(View.VISIBLE);
                loadImage(dataToPreload, imageTab[prevIndex]);
            }else{
                imageTab[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void loadImage(String dataToPreload, ImageView image){
        if (Patterns.WEB_URL.matcher(dataToPreload).matches()) {
            Picasso.with(activity).load(dataToPreload).fit().centerInside()
                    .placeholder(R.drawable.camera).into(image);
        }
    }
}
