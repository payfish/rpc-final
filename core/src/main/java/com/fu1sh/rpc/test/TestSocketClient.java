package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Food;
import com.fu1sh.rpc.api.Human;
import com.fu1sh.rpc.api.Woman;
import com.fu1sh.rpc.client.SocketClient;
import com.fu1sh.rpc.proxy.RpcClientProxy;

public class TestSocketClient {

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy(new SocketClient("127.0.0.1", 9999));
        Animal animalService = rpcClientProxy.getProxy(Animal.class);
        Human humanService = rpcClientProxy.getProxy(Human.class);
        Food food = new Food("狗粮", 20);
        Woman Mary = new Woman();
        String res2 = humanService.havingSexWith(Mary);
        String res = animalService.eat(food);
        System.out.println(res);
        System.out.println(res2);
    }

}
