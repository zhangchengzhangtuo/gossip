package com.gaolaozhuang.netty.code;

import com.gaolaozhuang.netty.model.NettyTransporter;
import com.gaolaozhuang.utils.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;
import static com.gaolaozhuang.utils.Constants.*;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Decoder extends ReplayingDecoder<State> {

    public Decoder(){
        super(State.MAGIC);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch(state()){
            case MAGIC:
                checkMagic(in.readShort());
                checkpoint(State.SIGN);
            case SIGN:

            case TYPE:
            case ID:
            case BODY_LENGTH:
            case BODY:
        }
    }

    private void checkMagic(short magic){
        if(Protocol.MAGIC!=magic){
            throw new DecoderException("magic vlaue "+magic+" is not equal "+Protocol.MAGIC);
        }
    }
}
