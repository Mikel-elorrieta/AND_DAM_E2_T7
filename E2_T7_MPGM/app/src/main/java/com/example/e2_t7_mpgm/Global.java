package com.example.e2_t7_mpgm;

import modelo.Users;

public class Global {

    public static Users user;


    public static Users getUser() {
        return user;
    }

    public static void setUser(Users user) {
        Global.user = user;
    }
}
