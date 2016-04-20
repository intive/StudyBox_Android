package com.blstream.studybox.auth.login;

import com.blstream.studybox.model.AuthCredentials;

public interface LoginInterface {

    void saveUser(AuthCredentials credentials);
    void deleteUser();
    boolean isUserLoggedIn();
    String getUserEmail();
    String getUserPassword();
    String getUserName();
    String getUserId();
    AuthCredentials getCredentials();
}
