package com.whaa.blog.chat.netty.client;

import com.whaa.blog.chat.netty.client.handler.LoginResponseHandler;
import com.whaa.blog.chat.netty.client.handler.MessageResponseHandler;
import com.whaa.blog.chat.netty.codec.PacketDecoder;
import com.whaa.blog.chat.netty.codec.PacketEncoder;
import com.whaa.blog.chat.netty.protocol.Spliter;
import com.whaa.blog.chat.netty.protocol.request.MessageRequestPacket;
import com.whaa.blog.common.thread.ThreadPoolManager;
import com.whaa.blog.common.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * created by wangzelong 2021-06-09 15:47
 */
public class NettyClient {

    static int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //测试沾包
//                        ch.pipeline().addLast(new FirstClientHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        // 4.建立连接
        collect(bootstrap, "127.0.0.1", 1000, MAX_RETRY);
    }

    private static void collect(Bootstrap bootstrap, final String addr, final int port, int retry) {
        bootstrap.connect(addr, port).addListener(future -> {
            //连接成功
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture)future).channel();
                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.out.println("重试结束！");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group()
                        .schedule(() -> collect(bootstrap, addr, port, retry - 1), delay, TimeUnit.SECONDS);
                ;
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ThreadPoolManager.getInstance().execute(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();
                    channel.writeAndFlush(new MessageRequestPacket(line));
                }
            }
        });
    }
}
