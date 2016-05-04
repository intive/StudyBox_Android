package com.blstream.studybox.components;

import android.app.Activity;
import android.content.Context;

import com.blstream.studybox.activities.BaseExamActivity;
import com.blstream.studybox.events.ImproveAllEvent;

import org.greenrobot.eventbus.EventBus;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Dialogs extends SweetAlertDialog {

    private Context context;

    public Dialogs(Context context, Activity owner) {
        super(context);
        this.context = context;
    }

    public Dialogs(Context context) {
        super(context);
    }
    public void modeDialogInit(final String deckId, final String deckName) {
        this.setTitleText("Wybierz tryb")
                .setCancelText("Nauka")
                .setConfirmText("Egzamin")
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
        this.setTitleText("To już wszystkie fiszki.")
                .setContentText("Czy rozpocząć naukę od nowa?")
                .setConfirmText("TAK")
                .setCancelText("NIE")
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
}
