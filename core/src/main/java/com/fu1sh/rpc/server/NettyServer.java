package com.fu1sh.rpc.server;

import com.fu1sh.rpc.codec.CommonDecoder;
import com.fu1sh.rpc.codec.CommonEncoder;
import com.fu1sh.rpc.handler.NettyServerHandler;
import com.fu1sh.rpc.register.DefaultServiceProvider;
import com.fu1sh.rpc.register.NacosServiceRegistry;
import com.fu1sh.rpc.register.ServiceProvider;
import com.fu1sh.rpc.register.ServiceRegistry;
import com.fu1sh.rpc.serializer.CommonSerializer;
import com.fu1sh.rpc.serializer.JsonSerializer;
import com.fu1sh.rpc.serializer.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyServer implements RpcServer{

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private CommonSerializer serializer;

    private int port;
    private String host;

    private final ServiceProvider serviceProvider;
    private final ServiceRegistry serviceRegistry;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
        serviceProvider = new DefaultServiceProvider();
        serviceRegistry = new NacosServiceRegistry();
    }
    @Override
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new CommonDecoder())
                                    .addLast(new CommonEncoder(serializer))
                                    .addLast(new NettyServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(host, port).sync();
            logger.info("服务端启动状态: {}", future.isDone());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        serviceProvider.register(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
    }
}
