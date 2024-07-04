package com.fu1sh.rpc.codec;

import com.fu1sh.rpc.entity.RpcRequest;
import com.fu1sh.rpc.entity.RpcResponse;
import com.fu1sh.rpc.enumeration.PackageType;
import com.fu1sh.rpc.enumeration.RpcError;
import com.fu1sh.rpc.exception.RpcException;
import com.fu1sh.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CommonDecoder extends ReplayingDecoder {

    private static final Logger logger = LoggerFactory.getLogger(CommonDecoder.class);

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magic = byteBuf.readInt();
        if (magic != MAGIC_NUMBER) {
            logger.error("Invalid magic number! {}", magic);
            throw new RpcException(RpcError.UNKNOWN_MAGIC_NUMBER);
        }
        int packageCode = byteBuf.readInt();
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            logger.error("Invalid package type! {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACK_TYPE);
        }
        int serializeCode = byteBuf.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializeCode);
        if (serializer == null) {
            logger.error("Invalid serialize code! {}", serializeCode);
            throw new RpcException(RpcError.UNKNOWN_SERIAL_CODE);
        }
        int len = byteBuf.readInt();
        byte[] data = new byte[len];
        byteBuf.readBytes(data);
        Object o = serializer.deserialize(data, packageClass);
        list.add(o);
    }
}
