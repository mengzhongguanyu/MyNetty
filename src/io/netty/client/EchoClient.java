package io.netty.client;

import com.netty.three.client.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.msgpack.MsgpackDecoder;
import io.netty.handler.codec.msgpack.MsgpackEncoder;

/**
 * Created by mypc on 2017/8/12.
 */
public class EchoClient {
    private final String host;
    private final int port;
    private final int sendNumber;

    public EchoClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }
    public void run()throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
           Bootstrap bootstrap = new Bootstrap();
           bootstrap.group(group).channel(NioSocketChannel.class)
                   .option(ChannelOption.TCP_NODELAY, true)
                   .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                   .handler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                           socketChannel.pipeline().addLast("mespack encoder", new MsgpackEncoder());
//                           socketChannel.pipeline().addLast(new EchoClientHandler(sendNumber));
                       }
                   });
        }finally {

        }
    }
}
