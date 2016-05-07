package com.blstream.studybox.components;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.RandomTestDialog;
import com.blstream.studybox.activities.BaseExamActivity;
import com.blstream.studybox.events.ImproveAllEvent;

import org.greenrobot.eventbus.EventBus;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Dialogs extends SweetAlertDialog {

    private Context context;
    private static final String TAG_DECK_ID = "deckId";
    private static final String TAG_DECK_NAME = "deckName";
    private static final String TAG_IN_EXAM= "inExam";

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
                        dismiss();
                        startExam(deckId, deckName, false);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dismissWithAnimation();
                        RandomTestDialog randomTestDialog = RandomTestDialog.newInstance(deckId, deckName, cardsAmount);
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
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

    private void startExam(final String deckId, final String deckName, boolean isExam) {
        Intent intent = new Intent(context, BaseExamActivity.class);
        intent.putExtra(TAG_DECK_ID, deckId);
        intent.putExtra(TAG_DECK_NAME, deckName);
        intent.putExtra(TAG_IN_EXAM, isExam);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpTransition();
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransition(){
        Activity activity = (Activity) context;
        Transition exitTrans = new Explode();
        activity.getWindow().setExitTransition(exitTrans);
        Transition reenterTrans = new Slide();
        activity.getWindow().setReenterTransition(reenterTrans);
    }
}
