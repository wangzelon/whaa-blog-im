package com.whaa.blog.chat.netty.protocol;

import lombok.Data;

/**
 * created by wangzelong 2021-06-17 11:51
 */
@Data
public abstract class Packet {

    private Byte version=1;

    public abstract Byte getCommand();

}
