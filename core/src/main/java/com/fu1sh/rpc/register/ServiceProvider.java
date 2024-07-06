package com.fu1sh.rpc.register;

public interface ServiceProvider {

    <T> void register(T service);

    Object getService(String serviceName);
}
