package com.whaa.blog;

import com.whaa.blog.chat.netty.protocol.JSONSerializer;
import com.whaa.blog.chat.netty.protocol.request.LoginRequestPacket;
import com.whaa.blog.chat.netty.protocol.Packet;
import com.whaa.blog.chat.netty.codec.PacketCodeC;
import com.whaa.blog.chat.netty.protocol.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
* created by wangzelong 2021-06-17 14:17
*/
public class blog {
    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123456");
        loginRequestPacket.setUsername("xiaomi");
        loginRequestPacket.setPassword("123456");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBufAllocator byteBufAllocator=ByteBufAllocator.DEFAULT;
        ByteBuf byteBuf = packetCodeC.encode(byteBufAllocator.buffer(),loginRequestPacket);
        System.out.println(byteBuf);
        Packet decodedPacket = packetCodeC.decode(byteBuf);
        System.out.println(decodedPacket);
        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }

    @Test
    public void test(){

    }


}
