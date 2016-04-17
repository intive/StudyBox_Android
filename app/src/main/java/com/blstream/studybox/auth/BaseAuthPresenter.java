package com.blstream.studybox.auth;

import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public abstract class BaseAuthPresenter<V extends BaseAuthView> extends MvpBasePresenter<V> {

    public abstract void validateCredential(AuthCredentials credentials);
    protected abstract void authenticate(final AuthCredentials credentials);
}
