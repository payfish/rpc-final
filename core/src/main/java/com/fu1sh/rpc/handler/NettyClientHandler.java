package com.fu1sh.rpc.handler;

import com.fu1sh.rpc.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        try {
            logger.info("接收到服务端消息：{}", msg);
            AttributeKey<RpcResponse> attributeKey = AttributeKey.valueOf("RpcResponse");
            ctx.channel().attr(attributeKey).set(msg);
            ctx.channel().close(); //接收到服务端消息后主动关闭channel
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("过程调用时出错：{}", cause.getMessage());
        ctx.close();
    }
}
