package com.blstream.studybox.exam_view;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.squareup.picasso.Picasso;

public class ImageTextDisplay implements Parcelable {

    private final int preloadImageCount;
    private int prevIndex;
    private int index;
    private final int height;
    private final int width;

    public ImageTextDisplay(int preloadImageCount, Activity activity) {
        this.preloadImageCount = preloadImageCount;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    protected ImageTextDisplay(Parcel in) {
        preloadImageCount = in.readInt();
        prevIndex = in.readInt();
        index = in.readInt();
        height = in.readInt();
        width = in.readInt();
    }

    public static final Creator<ImageTextDisplay> CREATOR = new Creator<ImageTextDisplay>() {
        @Override
        public ImageTextDisplay createFromParcel(Parcel in) {
            return new ImageTextDisplay(in);
        }

        @Override
        public ImageTextDisplay[] newArray(int size) {
            return new ImageTextDisplay[size];
        }
    };

    public ImageView[] init(FrameLayout frameLayout, TextView tvQuestion,
                            String[] imgToLoad, Activity activity){
        ImageView[] imageTab = setImageTab(frameLayout, activity.getApplicationContext());
        initPreloadImages(imgToLoad, imageTab, activity);
        setView(imageTab[0], tvQuestion, imgToLoad[0]);
        return imageTab;
    }

    public void initOnRestart(ImageView[] imageTab, TextView tvQuestion,
                              String[] imgToLoad, Activity activity){
        initPreloadImages(imgToLoad, imageTab, activity);
        setView(imageTab[0], tvQuestion, imgToLoad[0]);
    }

    private ImageView[] setImageTab(FrameLayout frameLayout, Context context){
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

    public void initPreloadImages(
            String[] imgToLoad, ImageView[] imageTab, Activity activity){
        for (int i = 0; i < preloadImageCount; i++) {
            imageTab[i].setVisibility(View.INVISIBLE);
            preloadImage(imgToLoad[i], imageTab[i], activity);
        }
    }

    public void changeData(String currentData, String dataToPreload,
                           TextView tv, ImageView[] imageTab, Activity activity) {
        if (Patterns.WEB_URL.matcher(currentData).matches()) {
            showImage(tv, imageTab);
        } else {
            showText(currentData, tv, imageTab);
        }
        preloadImage(dataToPreload, imageTab[prevIndex], activity);
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

    private void preloadImage(String dataToPreload, ImageView image, Activity activity){
        if (dataToPreload == null) {
            return;
        }
        if (Patterns.WEB_URL.matcher(dataToPreload).matches()) {
            Picasso.with(activity).load(dataToPreload).resize(width, height).centerInside()
                    .placeholder(R.drawable.camera).into(image);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(preloadImageCount);
        dest.writeInt(prevIndex);
        dest.writeInt(index);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
