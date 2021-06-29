package com.whaa.blog.chat.netty.protocol;

import com.whaa.blog.common.Session;
import io.netty.util.AttributeKey;

/**
 * created by wangzelong 2021-06-17 15:34
 */
public class Attributes {
    public static AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    public static AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
