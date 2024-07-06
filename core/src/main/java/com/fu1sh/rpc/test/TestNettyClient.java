package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Food;
import com.fu1sh.rpc.client.NettyClient;
import com.fu1sh.rpc.client.RpcClient;
import com.fu1sh.rpc.proxy.RpcClientProxy;
import com.fu1sh.rpc.serializer.KryoSerializer;

public class TestNettyClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        Animal animalService = rpcClientProxy.getProxy(Animal.class);
        Food food = new Food("猪食", 30);
        String s = animalService.eat(food);
//        String s1 = animalService.greet("fuish", "hello!");
        System.out.println(s);
//        System.out.println(s1);
    }
}
