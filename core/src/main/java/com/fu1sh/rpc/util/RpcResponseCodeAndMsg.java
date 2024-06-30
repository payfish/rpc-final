package com.fu1sh.rpc.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcResponseCodeAndMsg {

    SUCCESS(200, "方法调用成功！"),
    FAIL(404, "调用方法失败！");

    private final int code;
    private final String msg;

}
