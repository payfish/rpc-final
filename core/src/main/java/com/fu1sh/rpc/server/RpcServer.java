package com.fu1sh.rpc.server;

import com.fu1sh.rpc.serializer.CommonSerializer;

public interface RpcServer {
    void start();

    void setSerializer(CommonSerializer serializer);

    <T> void publishService(Object service, Class<T> serviceClass);
}
