package com.fu1sh.rpc.client;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.serializer.CommonSerializer;

public interface RpcClient {

    Object sendRequest(RpcRequest request);

    void setSerializer(CommonSerializer serializer);
}
