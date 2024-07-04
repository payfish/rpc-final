package com.fu1sh.rpc.entity;

import com.fu1sh.rpc.util.RpcResponseCodeAndMsg;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
public class RpcResponse<T> implements Serializable {

    public RpcResponse() {}

    //响应状态码
    private Integer code;
    //响应消息
    private String msg;
    //调用接口的方法返回的数据
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCodeAndMsg.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCodeAndMsg codeAndMsg) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(codeAndMsg.getCode());
        response.setMsg(codeAndMsg.getMsg());
        return response;
    }
}
