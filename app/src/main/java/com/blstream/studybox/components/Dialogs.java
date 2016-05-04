package com.blstream.studybox.components;

import android.app.Activity;
import android.content.Context;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.BaseExamActivity;
import com.blstream.studybox.events.ImproveAllEvent;

import org.greenrobot.eventbus.EventBus;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Dialogs extends SweetAlertDialog {

    private Context context;

    public Dialogs(Context context) {
        super(context);
        this.context = context;
    }

    public void modeDialogInit(final String deckId, final String deckName) {
        this.setTitleText(getString(R.string.choose_mode))
                .setCancelText(getString(R.string.study))
                .setConfirmText(getString(R.string.exam))
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dismissWithAnimation();
                        BaseExamActivity.start(getContext(), false, deckId, deckName, false);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dismissWithAnimation();
                        BaseExamActivity.start(getContext(), true, deckId, deckName, false);
                    }
                });
    }

    public void studyEndDialogInit() {
        this.setTitleText(getString(R.string.repeat_study_info))
                .setContentText(getString(R.string.repeat_study_question))
                .setConfirmText(getString(R.string.yes))
                        .setCancelText(getString(R.string.no))
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dismissWithAnimation();
                                ((Activity) context).finish();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dismissWithAnimation();
                                EventBus.getDefault().post(new ImproveAllEvent());
                            }
                        });
    }

    private String getString(int id) {
        return context.getResources().getString(id);
    }
}
