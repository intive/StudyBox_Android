package com.blstream.studybox.exam_view;

import com.blstream.studybox.model.database.Tip;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class QuestionPresenter extends MvpBasePresenter<QuestionView> {

    protected String question;
    protected List<Tip> prompts;

    @Override
    public void attachView(QuestionView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        EventBus.getDefault().unregister(this);
    }
}
