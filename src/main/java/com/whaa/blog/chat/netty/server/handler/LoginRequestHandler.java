package com.whaa.blog.chat.netty.server.handler;

import cn.hutool.core.util.RandomUtil;
import com.whaa.blog.chat.netty.protocol.request.LoginRequestPacket;
import com.whaa.blog.chat.netty.protocol.response.LoginResponsePacket;
import com.whaa.blog.common.Session;
import com.whaa.blog.common.util.LoginUtil;
import com.whaa.blog.common.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.Random;

/**
 * created by wangzelong 2021-06-29 11:44
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 收到客户端登录请求……");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUsername(loginRequestPacket.getUsername());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println("[" + loginRequestPacket.getUsername() + "]登录成功");
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }
        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private String randomUserId() {
        return RandomUtil.randomString(6);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
