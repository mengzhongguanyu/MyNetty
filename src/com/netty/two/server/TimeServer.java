package com.netty.two.server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by mypc on 2017/7/31.
 */
public class TimeServer {
    public void bind(int port) throws Exception{
        //创建两NioEventLoopGroup实例。
        //NioEventLoopGroup是个线程组。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //创建ServerBootstrap对象，它是Netty用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度。
            ServerBootstrap b = new ServerBootstrap();
            //调用ServerBootstrap的group方法，将两个NIO线程组当做入参传递到ServerBootstrap中。
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            ChannelFuture f = b.bind(port).sync();
            //等待服务锻炼路关闭之后main函数才退出。
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放线程池资源。
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
           arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
           arg0.pipeline().addLast(new StringDecoder());
           arg0.pipeline().addLast(new TimeServerHandler());
        }

    }

    public static void main(String[] args) throws Exception{
        int port = 8088;
        if (args != null && args.length > 0 ) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {

            }
        }
        new TimeServer().bind(port);
    }
}
