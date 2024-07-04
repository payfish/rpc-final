package com.fu1sh.rpc.client;

import com.fu1sh.rpc.codec.CommonDecoder;
import com.fu1sh.rpc.codec.CommonEncoder;
import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.handler.NettyClientHandler;
import com.fu1sh.rpc.serializer.CommonSerializer;
import com.fu1sh.rpc.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClient implements RpcClient{

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static { //在静态代码块中先配置好客户端
        bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
        .channel(NioSocketChannel.class)

        .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new CommonDecoder())
                        .addLast(new CommonEncoder(new JsonSerializer()))
                        .addLast(new NettyClientHandler());
            }
        });
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("客户端连接到服务器：{}，{}", host, port);
            Channel channel = future.channel();
            if (channel.isActive()) {
                channel.writeAndFlush(request).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info("客户端发送消息：{}", request.toString());
                    } else {
                        logger.error("发送消息时有错误发生，{}", future1.cause().getMessage());
                    }
                });
            }
            //阻塞等待直到channel关闭
            channel.closeFuture().sync();
            AttributeKey<RpcResponse> attributeKey = AttributeKey.valueOf("RpcResponse");
            RpcResponse rpcResponse = channel.attr(attributeKey).get();
            return rpcResponse.getData();
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生：", e);
        }
        return null;
    }
}
