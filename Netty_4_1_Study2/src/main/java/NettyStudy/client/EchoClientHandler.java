package NettyStudy.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018年11月19日
 * version: v1.0
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /*@Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer((ctx.channel().id() + " has joined!").getBytes(CharsetUtil.UTF_8)));
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        System.out.println(msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
