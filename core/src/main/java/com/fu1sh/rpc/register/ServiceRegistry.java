package com.fu1sh.rpc.register;

public interface ServiceRegistry {

    <T> void register(T service);

    Object getService(String serviceName);
}
