package com.fu1sh.rpc.entity;

import com.fu1sh.rpc.util.RpcResponseCodeAndMsg;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class RpcResponse<T> implements Serializable {

    //响应状态码
    private Integer code;
    //响应消息
    private String msg;
    //调用接口的方法返回的数据
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        return new RpcResponseBuilder<T>()
                .code(RpcResponseCodeAndMsg.SUCCESS.getCode())
                .msg(RpcResponseCodeAndMsg.SUCCESS.getMsg())
                .data(data).build();
    }

    public static <T> RpcResponse<T> fail(RpcResponseCodeAndMsg codeAndMsg) {
        return new RpcResponseBuilder<T>()
                .code(codeAndMsg.getCode())
                .msg(codeAndMsg.getMsg()).build();
    }
}
