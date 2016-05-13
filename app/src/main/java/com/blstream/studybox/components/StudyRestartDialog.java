package com.blstream.studybox.components;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.blstream.studybox.R;
import com.blstream.studybox.events.ImproveAllEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudyRestartDialog extends DialogFragment {

    public static StudyRestartDialog newInstance() {
        StudyRestartDialog instance = new StudyRestartDialog();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_study_restart, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.yes_button)
    public void startStudy(View view) {
        dismiss();
        EventBus.getDefault().post(new ImproveAllEvent());
    }

    @OnClick(R.id.no_button)
    public void startExam(View view) {
        dismiss();
        ((Activity) getContext()).finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransition() {
        Activity activity = (Activity) getContext();
        Transition exitTrans = new Explode();
        activity.getWindow().setExitTransition(exitTrans);
    }
}
