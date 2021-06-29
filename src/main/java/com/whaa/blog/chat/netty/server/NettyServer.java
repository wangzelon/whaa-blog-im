package com.whaa.blog.chat.netty.server;

import com.whaa.blog.chat.netty.codec.PacketDecoder;
import com.whaa.blog.chat.netty.codec.PacketEncoder;
import com.whaa.blog.chat.netty.demo.FirstServerHandler;
import com.whaa.blog.chat.netty.protocol.Spliter;
import com.whaa.blog.chat.netty.server.handler.LoginRequestHandler;
import com.whaa.blog.chat.netty.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

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
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        //测试沾包, LineBasedFrameDecoder行拆包器
//                        ch.pipeline().addLast(new LineBasedFrameDecoder(100));
//                        ch.pipeline().addLast(new FirstServerHandler());
                        ch.pipeline().addLast(new Spliter());  //拆包器
                        ch.pipeline().addLast(new PacketDecoder());  //解码器
                        ch.pipeline().addLast(new LoginRequestHandler());//登录处理器
                        ch.pipeline().addLast(new MessageRequestHandler());//消息处理器
                        ch.pipeline().addLast(new PacketEncoder()); //编码器
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
