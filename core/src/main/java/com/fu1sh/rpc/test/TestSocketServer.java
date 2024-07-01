package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Dog;
import com.fu1sh.rpc.api.Human;
import com.fu1sh.rpc.api.Man;
import com.fu1sh.rpc.register.DefaultServiceRegistry;
import com.fu1sh.rpc.register.ServiceRegistry;
import com.fu1sh.rpc.server.SocketServer;

public class TestSocketServer  {

    public static void main(String[] args) {
        Animal dog = new Dog();
        Human man = new Man("Tom");
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(dog);
        serviceRegistry.register(man);
        SocketServer server = new SocketServer(serviceRegistry);
        server.start(9999);
    }
}
