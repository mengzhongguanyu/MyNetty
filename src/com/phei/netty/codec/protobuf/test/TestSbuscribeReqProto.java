package com.phei.netty.codec.protobuf.test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.phei.netty.codec.protobuf.SubscribeReqProto;

import java.util.Objects;

/**
 * Created by mypc on 2017/8/12.
 */
public class TestSbuscribeReqProto {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("Lilinfeng");
        builder.setBroductName("Netty Book");
        builder.setAddress("BeiJing li");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : "+req.toString());
        SubscribeReqProto.SubscribeReq req1 = decode(encode(req));
        System.out.println("After decode : " + req.toString());
        System.out.println("Assert equal : ->" + Objects.equals(req,req1));
    }
}
