package com.fu1sh.rpc.test;

import com.fu1sh.rpc.api.Animal;
import com.fu1sh.rpc.api.Dog;
import com.fu1sh.rpc.server.SocketServer;

public class TestSocketServer  {

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        Animal dog = new Dog();
        server.register(dog, 9999);
    }
}
