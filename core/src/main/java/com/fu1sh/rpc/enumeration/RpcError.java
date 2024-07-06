package com.fu1sh.rpc.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_NOT_FOUND("找不到服务"),
    REGISTER_NOT_FOUND("未设置序列化器"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACES("服务没有实现任何接口"),
    UNKNOWN_MAGIC_NUMBER("不识别的协议包"),
    UNKNOWN_PACK_TYPE("不识别的数据包"),
    UNKNOWN_SERIAL_CODE("不识别的反序列化器"),
    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("连接注册中心失败"),
    SERVICE_RIGSTER_FAILED("注册服务失败"),
    CLIENT_CONNECT_SERVER_FAILURE("客户端连接失败");


    private final String msg;

}

