package com.blstream.studybox.components;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
        return new StudyRestartDialog();
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
    public void restartStudy(View view) {
        dismiss();
        EventBus.getDefault().post(new ImproveAllEvent());
    }

    @OnClick(R.id.no_button)
    public void finishStudy(View view) {
        dismiss();
        ((Activity) getContext()).finish();
    }
}
