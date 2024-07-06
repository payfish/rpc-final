package com.fu1sh.rpc.handler;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.register.ServiceProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandlerThread implements Runnable {

    private Socket socket;
    private RequestHandler requestHandler;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceProvider serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            Object result = requestHandler.handle(rpcRequest);
            oos.writeObject(RpcResponse.success(result));
            oos.flush();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
