package com.gaolaozhuang.netty.code;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.netty.model.CommonBody;
import com.gaolaozhuang.netty.model.NettyTransporter;
import com.gaolaozhuang.netty.serialization.FastjsonSerializer;
import com.gaolaozhuang.netty.serialization.Serializer;
import com.gaolaozhuang.utils.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.AttributeKey;

import java.util.List;
import static com.gaolaozhuang.utils.Constants.*;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Decoder extends ReplayingDecoder<State> {

    private final static AttributeKey<ProtocolHeader> protocolHeaderAttributeKey=AttributeKey.newInstance("protocol.header");

    private Serializer serializer;

    public Decoder(){
        super(State.MAGIC);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch(state()){
            case MAGIC:
                checkMagic(in.readShort());
                ProtocolHeader protocolHeader=new ProtocolHeader();
                ctx.channel().attr(protocolHeaderAttributeKey).set(protocolHeader);
                checkpoint(State.SIGN);
            case SIGN:
                byte sign=in.readByte();
                ProtocolHeader signProtocolHeader=ctx.channel().attr(protocolHeaderAttributeKey).get();
                signProtocolHeader.setSign(sign);
                ctx.channel().attr(protocolHeaderAttributeKey).set(signProtocolHeader);
                checkpoint(State.TYPE);
            case TYPE:
                byte type=in.readByte();
                ProtocolHeader typeProtocolHeader=ctx.channel().attr(protocolHeaderAttributeKey).get();
                typeProtocolHeader.setType(type);
                ctx.channel().attr(protocolHeaderAttributeKey).set(typeProtocolHeader);
                checkpoint(State.ID);
            case ID:
                long id=in.readLong();
                ProtocolHeader idProtocolHeader=ctx.channel().attr(protocolHeaderAttributeKey).get();
                idProtocolHeader.setInvokeId(id);
                ctx.channel().attr(protocolHeaderAttributeKey).set(idProtocolHeader);
                checkpoint(State.BODY_LENGTH);
            case BODY_LENGTH:
                int bodyLength=in.readInt();
                ProtocolHeader bodyLengthProtocolHeader=ctx.channel().attr(protocolHeaderAttributeKey).get();
                bodyLengthProtocolHeader.setBodyLength(bodyLength);
                ctx.channel().attr(protocolHeaderAttributeKey).set(bodyLengthProtocolHeader);
                checkpoint(State.BODY);
            case BODY:
                ProtocolHeader bodyProtocolHeader=ctx.channel().attr(protocolHeaderAttributeKey).get();
                byte [] bytes=new byte[bodyProtocolHeader.getBodyLength()];
                in.readBytes(bytes);
                CommonBody commonBody= (CommonBody) serializer.readObject(bytes, Init.getCommonBodyClass(bodyProtocolHeader.getType()));
                NettyTransporter nettyTransporter=new NettyTransporter();
                nettyTransporter.setSign(bodyProtocolHeader.getSign());
                nettyTransporter.setType(bodyProtocolHeader.getType());
                nettyTransporter.setInvokeId(bodyProtocolHeader.getInvokeId());
                nettyTransporter.setCommonBody(commonBody);
                out.add(nettyTransporter);
                break;
            default:break;
        }
        checkpoint(State.MAGIC);
    }

    private void checkMagic(short magic){
        if(Protocol.MAGIC!=magic){
            throw new DecoderException("magic vlaue "+magic+" is not equal "+Protocol.MAGIC);
        }
    }
}
