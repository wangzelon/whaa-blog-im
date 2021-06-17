package com.whaa.blog.chat.netty.protocol;

/**
 * created by wangzelong 2021-06-17 11:53
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE =4;
}

