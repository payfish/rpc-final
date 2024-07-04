package com.fu1sh.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.enumeration.SerializerCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonSerializer implements CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes;
        try {
            bytes = mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.error("Json serialize error", e);
            return null;
        }
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        Object obj;
        try {
            obj = mapper.readValue(bytes, clazz);
            if (obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            logger.error("Json deserialize error", e);
            return null;
        }
    }

    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) obj;
        for(int i = 0; i < rpcRequest.getParamTypes().length; i ++) {
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParams()[i].getClass())) {
                byte[] bytes = mapper.writeValueAsBytes(rpcRequest.getParams()[i]);
                rpcRequest.getParams()[i] = mapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }
    @Override
    public int getCode() {
        return SerializerCode.JSON_SERIALIZER.getCode();
    }
}
