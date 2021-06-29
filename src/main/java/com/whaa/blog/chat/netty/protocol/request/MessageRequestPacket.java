package com.whaa.blog.chat.netty.protocol.request;

import com.whaa.blog.chat.netty.protocol.Command;
import com.whaa.blog.chat.netty.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * created by wangzelong 2021-06-17 15:31
 */
@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

    private String message;

    private String toUserId;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
