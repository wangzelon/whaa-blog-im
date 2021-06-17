package com.whaa.blog.chat.netty.protocol.request;

import com.whaa.blog.chat.netty.protocol.Packet;
import lombok.Data;

import static com.whaa.blog.chat.netty.protocol.Command.*;

/**
 * created by wangzelong 2021-06-17 11:56
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;
    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
