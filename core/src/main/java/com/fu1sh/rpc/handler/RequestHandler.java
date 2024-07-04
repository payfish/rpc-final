package com.fu1sh.rpc.handler;

import com.fu1sh.rpc.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(Object service, RpcRequest rpcRequest) {
        Object result = null;
        try {
            result = invokeTargetMethod(service, rpcRequest);
            logger.info("服务调用方法成功！");
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error("服务调用方法失败");
        }
        return result;
    }

    private Object invokeTargetMethod(Object service, RpcRequest rpcRequest) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return method.invoke(service, rpcRequest.getParams());
    }
}
