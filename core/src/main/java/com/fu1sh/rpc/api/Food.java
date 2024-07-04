package com.fu1sh.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Food implements Serializable {

    public Food() {}

    private String name;
    private int price;
}
