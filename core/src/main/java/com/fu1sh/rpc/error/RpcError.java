package com.fu1sh.rpc.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_NOT_FOUND("找不到服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACES("服务没有实现任何接口"),
    SERVICE_INVOCATION_FAILURE("服务调用失败");


    private final String msg;

}
