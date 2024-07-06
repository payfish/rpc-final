package com.fu1sh.rpc.handler;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.register.DefaultServiceProvider;
import com.fu1sh.rpc.register.ServiceProvider;
import com.fu1sh.rpc.util.RpcResponseCodeAndMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final ServiceProvider serviceProvider;

    static {
        serviceProvider = new DefaultServiceProvider();
    }

    public Object handle(RpcRequest rpcRequest) {
        Object result = null;
        Object service = serviceProvider.getService(rpcRequest.getInterfaceName());
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
            return RpcResponse.fail(RpcResponseCodeAndMsg.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParams());
    }
}
