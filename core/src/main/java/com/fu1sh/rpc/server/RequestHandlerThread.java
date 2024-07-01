package com.fu1sh.rpc.server;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.register.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandlerThread implements Runnable {

    private Socket socket;
    private ServiceRegistry serviceRegistry;
    private RequestHandler requestHandler;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            Object service = serviceRegistry.getService(rpcRequest.getInterfaceName());
            Object result = requestHandler.handle(service, rpcRequest);
            oos.writeObject(RpcResponse.success(result));
            oos.flush();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
