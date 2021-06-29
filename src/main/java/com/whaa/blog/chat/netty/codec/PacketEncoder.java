package com.whaa.blog.chat.netty.codec;

import com.whaa.blog.chat.netty.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * created by wangzelong 2021-06-29 11:47
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
