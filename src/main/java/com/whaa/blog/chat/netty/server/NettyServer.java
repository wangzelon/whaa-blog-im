package com.whaa.blog.chat.netty.server;

import com.whaa.blog.chat.netty.event.InBoundHandlerA;
import com.whaa.blog.chat.netty.event.InBoundHandlerB;
import com.whaa.blog.chat.netty.event.InBoundHandlerC;
import com.whaa.blog.chat.netty.event.OutBoundHandlerA;
import com.whaa.blog.chat.netty.event.OutBoundHandlerB;
import com.whaa.blog.chat.netty.event.OutBoundHandlerC;
import com.whaa.blog.chat.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * created by wangzelong 2021-06-08 16:56
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new ServerHandler());
                        ch.pipeline().addLast(new InBoundHandlerA());
                        ch.pipeline().addLast(new InBoundHandlerB());
                        ch.pipeline().addLast(new InBoundHandlerC());

                        ch.pipeline().addLast(new OutBoundHandlerA());
                        ch.pipeline().addLast(new OutBoundHandlerB());
                        ch.pipeline().addLast(new OutBoundHandlerC());
                    }
                });
        bind(serverBootstrap, 1000);
    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口绑定" + port + "成功");
            } else {
                System.out.println("端口绑定" + port + "失败");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
