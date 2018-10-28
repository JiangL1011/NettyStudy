package NettyStudy.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018/10/28
 * version: v1.0
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            /*
             * 如果该事件表示握手成功，则从该ChannelPipeline中移除HttpRequestHandler，
             * 因为将不会接收到任何HTTP消息了
             * */
            ctx.pipeline().remove(HttpRequestHandler.class);

            // 通知所有已经连接的WebSocket客户端新的客户端已经连接上了
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + "joined"));

            // 将新的WebSocket Channel添加到ChannelGroup中，以便它可以接收到所有的消息
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        // 增加消息的引用计数，并将它写到ChannelGroup中所有已经连接的客户端
        group.writeAndFlush(msg.retain());
    }
}
