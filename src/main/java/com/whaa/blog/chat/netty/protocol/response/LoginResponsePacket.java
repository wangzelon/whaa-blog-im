package com.whaa.blog.chat.netty.protocol.response;

import com.whaa.blog.chat.netty.protocol.Packet;
import lombok.Data;

import static com.whaa.blog.chat.netty.protocol.Command.LOGIN_RESPONSE;

/**
 * created by wangzelong 2021-06-17 15:13
 */
@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
