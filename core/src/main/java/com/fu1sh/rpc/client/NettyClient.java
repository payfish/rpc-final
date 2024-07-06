package com.fu1sh.rpc.client;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.enumeration.RpcError;
import com.fu1sh.rpc.exception.RpcException;
import com.fu1sh.rpc.register.NacosServiceRegistry;
import com.fu1sh.rpc.register.ServiceRegistry;
import com.fu1sh.rpc.serializer.CommonSerializer;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

public class NettyClient implements RpcClient{

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private final ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;

    public NettyClient() {
        this.serviceRegistry = new NacosServiceRegistry();
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(request.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if (channel.isActive()) {
                channel.writeAndFlush(request).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info("客户端发送消息：{}", request);
                    } else {
                        logger.error("发送消息时有错误发生，{}", future1.cause().getMessage());
                    }
                });
                //阻塞等待直到channel关闭，关闭后AttributeKey才被设置好
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> attributeKey = AttributeKey.valueOf("RpcResponse");
                RpcResponse rpcResponse = channel.attr(attributeKey).get();
                result.set(rpcResponse.getData());
            } else {
                System.exit(0);
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生：", e);
        }
        return result.get();
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
