package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Dog;
import com.fu1sh.rpc.api.Human;
import com.fu1sh.rpc.api.Man;
import com.fu1sh.rpc.register.DefaultServiceProvider;
import com.fu1sh.rpc.register.ServiceProvider;
import com.fu1sh.rpc.server.SocketServer;

public class TestSocketServer  {

    public static void main(String[] args) {
        Animal dog = new Dog();
        Human man = new Man("Tom");
        ServiceProvider serviceProvider = new DefaultServiceProvider();
        serviceProvider.register(dog);
        serviceProvider.register(man);
        SocketServer server = new SocketServer(serviceProvider);
        server.start();
    }
}
