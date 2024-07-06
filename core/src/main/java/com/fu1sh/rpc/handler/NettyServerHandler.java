package com.fu1sh.rpc.handler;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.register.DefaultServiceProvider;
import com.fu1sh.rpc.register.ServiceProvider;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler = new RequestHandler();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) {
        try {
            logger.info("接收到客户端请求：{}", msg);
            Object handled = requestHandler.handle(msg);
            ChannelFuture channelFuture = ctx.writeAndFlush(RpcResponse.success(handled));
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("处理过程调用时出错：{}", cause.getMessage());
        ctx.close();
    }
}
