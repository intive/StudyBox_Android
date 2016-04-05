package com.blstream.studybox.login;

import com.blstream.studybox.model.AuthCredentials;

public interface LoginInterface {

    void saveUser(AuthCredentials credentials);
    void deleteUser();
    boolean isUserLoggedIn();
    String getUserEmail();
    String getUserPassword();
}
