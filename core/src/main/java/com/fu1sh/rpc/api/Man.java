package com.fu1sh.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Man implements Human{

    private String name;

    @Override
    public String havingSexWith(Human human) {
        return this.getClass().getCanonicalName() + " " + name + " Having Sex With "
                + human.getClass().getCanonicalName();
    }
}
