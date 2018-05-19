package me.kwiatkowsky.PrideChat.web;

import me.kwiatkowsky.PrideChat.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Session {

    private User user;
    private boolean isLogged;

    Session(){

    }

    public Session(User user, boolean isLogged) {
        this.user = user;
        this.isLogged = isLogged;
    }

    @PostConstruct
    public void Build(){
        this.user = null;
        this.isLogged = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}
