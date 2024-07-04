package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Dog;
import com.fu1sh.rpc.register.DefaultServiceRegistry;
import com.fu1sh.rpc.register.ServiceRegistry;
import com.fu1sh.rpc.server.NettyServer;

public class TestNettyServer {
    public static void main(String[] args) {
        Animal dog = new Dog();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(dog);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
