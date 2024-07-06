package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Dog;
import com.fu1sh.rpc.register.DefaultServiceProvider;
import com.fu1sh.rpc.register.NacosServiceRegistry;
import com.fu1sh.rpc.register.ServiceProvider;
import com.fu1sh.rpc.register.ServiceRegistry;
import com.fu1sh.rpc.serializer.KryoSerializer;
import com.fu1sh.rpc.server.NettyServer;

public class TestNettyServer {
    public static void main(String[] args) {
        Animal dog = new Dog();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new KryoSerializer());
        server.publishService(dog, Animal.class);
        server.start();
    }
}
