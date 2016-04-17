package com.blstream.studybox.decks_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;

import com.blstream.studybox.activities.ExamActivity;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.login.LoginManager;
import com.blstream.studybox.model.database.Decks;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;


import java.util.Collections;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by Łukasz on 2016-03-03.
 */
public class DecksPresenter extends MvpBasePresenter<DecksView> implements RequestListener<String> {

    private DataHelper dataHelper = new DataHelper();
    private boolean pullToRefresh;
    private Context context;

    public DecksPresenter(Context context){
        this.context = context;
    }

    public void onViewPrepared() {
        loadDecks(false);
    }

    public void loadDecks(boolean pullToRefresh) {
        LoginManager loginManager= new LoginManager(context);
        this.pullToRefresh = pullToRefresh;
        if (pullToRefresh) {
            dataHelper.deleteAllDecks();
            if(loginManager.isUserLoggedIn()){
                dataHelper.downloadUserDecks(this, context);
            }else{
                dataHelper.downloadPublicDecks(this);
            }
        } else {
            setDecks();
        }
    }

    @Override
    public void onSuccess(String response) {
        LoginManager loginManager= new LoginManager(context);
        if (isViewAttached()) {
            if(loginManager.isUserLoggedIn()){
                getView().setData(dataHelper.getDecks());
            }else{
                getView().setData(dataHelper.getPublicDecks());
            }
            getView().showLoading(false);
            getView().showContent();
        }
    }

    @Override
    public void onFailure(RetrofitError error) {
        getView().showError(error, pullToRefresh);
    }

    public void onDeckClicked(int position, View view) {
        LoginManager loginManager = new LoginManager(context);
        String deckId;
        String deckName;

        if(loginManager.isUserLoggedIn()){
            deckId = dataHelper.getDecks().get(position).getDeckId();
            deckName = dataHelper.getDecks().get(position).getName();
        }else{
            deckId = dataHelper.getPublicDecks().get(position).getDeckId();
            deckName = dataHelper.getPublicDecks().get(position).getName();
        }


        Context context = view.getContext();
        Intent intent = new Intent(context, ExamActivity.class);
        intent.putExtra("deckId", deckId);
        intent.putExtra("deckName", deckName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context).toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    public void attachView(DecksView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    private void setDecks() {
        LoginManager loginManager= new LoginManager(context);
        
        if (isViewAttached()) {
            if (loginManager.isUserLoggedIn()) {
                List<Decks> decksList = dataHelper.getDecks();
                if (decksList.isEmpty()) {
                    dataHelper.downloadUserDecks(this, context);
                } else {
                    getView().setData(decksList);
                }
            } else {
                List<Decks> decksList = dataHelper.getPublicDecks();
                dataHelper.downloadPublicDecks(this);
                getView().setData(decksList);
            }
        }
    }
}
