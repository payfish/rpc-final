package com.fu1sh.rpc.client;

import com.fu1sh.rpc.entity.RpcRequest;

public interface RpcClient {
    Object sendRequest(RpcRequest request);
}
