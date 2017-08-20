package com.phei.netty.codec.protobuf.client;

import com.phei.netty.codec.protobuf.SubscribeReqProto;
import com.phei.netty.codec.protobuf.SubscribeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by mypc on 2017/8/12.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10 ; i ++){
            ctx.write(subReq(i));
        }
        ctx.flush();
    }
    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(i);
        builder.setUserName("Lilinfeng");
        builder.setBroductName("Netty Book For");
        builder.setAddress("BeiJing Li");
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server Response : [" + msg +"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
