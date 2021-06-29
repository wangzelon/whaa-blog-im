package com.whaa.blog.common.util;

import com.whaa.blog.chat.netty.protocol.Attributes;
import com.whaa.blog.common.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by wangzelong 2021-06-29 17:07
 */
public class SessionUtil {

    private static final Map<String, Channel> userMap = new ConcurrentHashMap<>(16);

    public static void bindSession(Session session, Channel channel) {
        userMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userMap.get(userId);
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }
}
