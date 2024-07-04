package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Food;
import com.fu1sh.rpc.client.NettyClient;
import com.fu1sh.rpc.proxy.RpcClientProxy;

public class TestNettyClient {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy(new NettyClient("127.0.0.1", 9999));
        Animal animalService = rpcClientProxy.getProxy(Animal.class);
        Food food = new Food("猪食", 30);
        String s = animalService.eat(food);
        System.out.println(s);
    }
}
