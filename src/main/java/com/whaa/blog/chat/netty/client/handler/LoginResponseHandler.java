package com.whaa.blog.chat.netty.client.handler;

import com.whaa.blog.chat.netty.protocol.response.LoginResponsePacket;
import com.whaa.blog.common.Session;
import com.whaa.blog.common.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * created by wangzelong 2021-06-29 11:38
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String username = loginResponsePacket.getUsername();
        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + username + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //        LoginRequestPacket loginRequestPacket=new LoginRequestPacket();
        //        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        //        loginRequestPacket.setUsername("admin");
        //        loginRequestPacket.setPassword("123456");
        //        // 写数据
        //        ctx.channel().writeAndFlush(loginRequestPacket);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
