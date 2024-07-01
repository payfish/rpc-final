package com.fu1sh.rpc.exception;

import com.fu1sh.rpc.error.RpcError;

public class RpcException extends RuntimeException {

    public RpcException(RpcError error) {super(error.getMsg());}
}
