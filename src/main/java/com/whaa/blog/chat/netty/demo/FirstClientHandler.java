package com.whaa.blog.chat.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * created by wangzelong 2021-06-15 14:14
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ":客户端写数据");
        for (int i = 0; i < 1000; i++) {
            ByteBuf byteBuf = getByteBuf(ctx,i+1);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, int sqe) {
        ByteBuf buffer = ctx.alloc().buffer();
        // 2. 准备数据，指定字符串的字符集为 utf-8
        String str = "你好，Whaa" + sqe + "!\n";
        byte[] bytes = str.getBytes(Charset.forName("utf-8"));
        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }
}
