package com.whaa.blog.chat.netty.codec;

import com.whaa.blog.chat.netty.protocol.Packet;
import com.whaa.blog.chat.netty.protocol.request.LoginRequestPacket;
import com.whaa.blog.chat.netty.protocol.request.MessageRequestPacket;
import com.whaa.blog.chat.netty.protocol.response.LoginResponsePacket;
import com.whaa.blog.chat.netty.protocol.response.MessageResponsePacket;
import com.whaa.blog.chat.netty.protocol.serializer.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.whaa.blog.chat.netty.protocol.Command.*;

/**
 * created by wangzelong 2021-06-17 14:03
 */
public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        serializerMap = new HashMap<>();
        serializerMap.put(Serializer.DEFAULT.getSerializerAlgorithm(), Serializer.DEFAULT);
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
