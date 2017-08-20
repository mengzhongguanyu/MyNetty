package io.netty.handler.codec.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Created by mypc on 2017/8/5.
 * 负责将Object类型的POJO对象编码成byte数组。
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack msgPack = new MessagePack();
        byte[] raw = msgPack.write(o);
        byteBuf.writeBytes(raw);
    }
}
