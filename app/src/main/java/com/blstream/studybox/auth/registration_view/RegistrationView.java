package com.blstream.studybox.auth.registration_view;

import com.blstream.studybox.auth.login_view.LoginView;

/**
 * Created by Marek Macko on 12.04.2016.
 */
public interface RegistrationView extends LoginView {

    void showPasswordInconsistent();
}
