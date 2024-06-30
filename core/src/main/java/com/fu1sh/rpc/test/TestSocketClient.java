package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Food;
import com.fu1sh.rpc.client.SocketClient;
import com.fu1sh.rpc.proxy.RpcClientProxy;

public class TestSocketClient {

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 9999);
        Animal animalService = rpcClientProxy.getProxy(Animal.class);
        Food food = new Food("狗粮", 20);
        String res = animalService.eat(food);
        System.out.println(res);
    }

}
