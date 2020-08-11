package com.rest.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@EqualsAndHashCode
public class MyUserCash {
    String username;
    String password;
    public boolean rememberme;

    public MyUserCash() {
    }

    public MyUserCash(String username, String password, boolean rememberme) {
        this.username = username;
        this.password = password;
        this.rememberme = rememberme;
    }

}
