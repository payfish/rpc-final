package com.fu1sh.rpc.enumeration;

import lombok.Getter;

@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

    PackageType(int code) {
        this.code = code;
    }
}
