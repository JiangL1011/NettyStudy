package NettyStudy.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018年11月19日
 * version: v1.0
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    //    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static List<ChannelHandlerContext> contexts = new ArrayList<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("进入 channelRead");
        ByteBuf received = (ByteBuf) msg;
        if ((received.toString(CharsetUtil.UTF_8)).equals("exit")) {
            ctx.writeAndFlush("Exit successfully!".getBytes(CharsetUtil.UTF_8))
                    .addListener(ChannelFutureListener.CLOSE);
        } else {
            for (ChannelHandlerContext context : contexts) {
                if (context != ctx) {
                    context.writeAndFlush(received);
                    received.retain();
                }
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("进入 channelReadComplete");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("进入 handlerAdded");
        Channel inChannel = ctx.channel();
        for (ChannelHandlerContext context : contexts) {
            context.writeAndFlush((inChannel.id() + " has joined!").getBytes(CharsetUtil.UTF_8));
        }
        contexts.add(ctx);
        System.err.println(inChannel.id() + " has joined! " + contexts.size() + " connections now!");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("进入 handlerRemoved");
        Channel outChannel = ctx.channel();
        for (ChannelHandlerContext context : contexts) {
            if (context != ctx) {
                context.writeAndFlush((outChannel.id() + "has exited!").getBytes(CharsetUtil.UTF_8));
            }
        }
        contexts.remove(ctx);
        System.err.println(outChannel.id() + " has exited! " + contexts.size() + " connections now!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("进入 exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}
