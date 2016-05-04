package com.blstream.studybox.components;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.blstream.studybox.R;
import com.blstream.studybox.RandomTestDialog;
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

    public void modeDialogInit(final String deckId, final String deckName, final int cardsAmount) {
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
                        RandomTestDialog randomTestDialog = RandomTestDialog.newInstance(deckId, deckName, cardsAmount);
                        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        randomTestDialog.show(fragmentManager, "RandomTestDialog");
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
