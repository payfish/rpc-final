package com.fu1sh.rpc.client;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.serializer.CommonSerializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements RpcClient{

    private String host;
    private int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public Object sendRequest(RpcRequest request) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {

    }
}
