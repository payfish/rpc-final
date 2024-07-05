package com.fu1sh.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON_SERIALIZER(1),
    KRYO_SERIALIZER(0);

    private final int code;
}
