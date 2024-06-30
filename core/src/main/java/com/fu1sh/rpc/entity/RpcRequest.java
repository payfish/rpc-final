package com.fu1sh.rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RpcRequest implements Serializable {

    //请求的接口名
    private String interfaceName;
    //请求的方法名
    private String methodName;
    //请求参数
    private Object[] params;
    //请求参数的类型
    private Class<?>[] paramTypes;

}
