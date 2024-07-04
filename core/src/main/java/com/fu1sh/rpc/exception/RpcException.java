package com.fu1sh.rpc.exception;

import com.fu1sh.rpc.enumeration.RpcError;

public class RpcException extends RuntimeException {

    public RpcException(RpcError error) {super(error.getMsg());}
}
