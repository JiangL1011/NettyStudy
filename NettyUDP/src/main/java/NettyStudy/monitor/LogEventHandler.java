package NettyStudy.monitor;

import NettyStudy.EventPOJO.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    10/30/2018
 * version: v1.0
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append(event.getReceivedTimestamp())
                .append(" [")
                .append(event.getSource().toString())
                .append("] [")
                .append(event.getLogFile())
                .append("] : ")
                .append(event.getMsg());
        System.out.println(builder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
