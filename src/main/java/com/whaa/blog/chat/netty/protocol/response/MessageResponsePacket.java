package com.whaa.blog.chat.netty.protocol.response;

import com.whaa.blog.chat.netty.protocol.Command;
import com.whaa.blog.chat.netty.protocol.Packet;
import lombok.Data;

/**
 * created by wangzelong 2021-06-17 15:32
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
