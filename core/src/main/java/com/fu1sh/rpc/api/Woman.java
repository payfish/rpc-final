package com.fu1sh.rpc.api;

import java.io.Serializable;

public class Woman implements Human, Serializable {
    @Override
    public String havingSexWith(Human human) {
        return "";
    }
}
