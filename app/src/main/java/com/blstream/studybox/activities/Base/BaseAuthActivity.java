package com.blstream.studybox.activities.base;

import android.support.design.widget.TextInputEditText;

import com.blstream.studybox.auth.BaseAuthView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public abstract class BaseAuthActivity<V extends BaseAuthView, P extends MvpBasePresenter<V>>
        extends BaseViewStateActivity<V, P> {

    protected final static float ENABLED_BUTTON_ALPHA = 1.0f;
    protected final static float DISABLED_BUTTON_ALPHA = 0.5f;

    protected abstract void setError(String message);
    protected abstract void setFieldError(TextInputEditText field, String message);
    protected abstract void setFormEnabled(boolean enabled);
}
