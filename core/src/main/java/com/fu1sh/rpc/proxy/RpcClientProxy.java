package com.fu1sh.rpc.proxy;

import com.fu1sh.rpc.client.SocketClient;
import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.test.TestSocketClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClientProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = RpcRequest.builder()
                .methodName(method.getName())
                .interfaceName(method.getDeclaringClass().getName())
                .params(args)
                .paramTypes(method.getParameterTypes())
                .build();
        SocketClient client = new SocketClient();
        RpcResponse rpcResponse = (RpcResponse) client.sendRequest(request, host, port);
        return rpcResponse.getData();
    }


    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    private int port;
    private String host;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
