package com.example.indq_events.data.local.auth;


import com.example.indq_events.data.local.LocalStorage;
import com.example.indq_events.data.local.auth.AuthDictionary;

import javax.inject.Inject;

public class Auth {

    LocalStorage localStorage;
    String login_file = AuthDictionary.LOGIN_FILE;

    @Inject
    public Auth(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    public String getAuthLocalStorage(String name){
        return localStorage.getValue(login_file, name);
    }

    public void setAuthLocalStorage(String name, String data){
        localStorage.setValue(login_file, name, data);
    }

    public void setToken(String token){
        setAuthLocalStorage(AuthDictionary.TOKEN, token);
    }


    public String getToken() {
        return getAuthLocalStorage(AuthDictionary.TOKEN);
    }
}

