package com.whaa.blog.chat.util;

import com.whaa.blog.chat.netty.protocol.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * created by wangzelong 2021-06-17 15:34
 */
public class LoginUtil {
    /**
     * 标记登录标识
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断是否登录
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
