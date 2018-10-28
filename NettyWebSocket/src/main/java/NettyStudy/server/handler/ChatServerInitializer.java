package NettyStudy.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018/10/28
 * version: v1.0
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline().addLast(
                /*
                 * 将字节解码为HttpRequest、HttpContent和LastHttpContent。
                 * 并将HttpRequest、HttpContent和LastHttpContent编码为字节。
                 * */
                new HttpServerCodec(),
                // 写入一个文件的内容
                new ChunkedWriteHandler(),
                /*
                 * 将一个HttpMessage和跟随它的多个HttpContent聚合为单个FullHttpRequest
                 * 或者FullHttpResponse（取决于它是被用来处理请求还是响应）。安装了这个之后，
                 * ChannelPipeline中的下一个ChannelHandler将只会收到完整的HTTP请求或响应。
                 * */
                new HttpObjectAggregator(64 * 1024),
                // 处理FullHttpRequest（那些不发送到/ws URI的请求）
                new HttpRequestHandler("/ws"),
                /*
                 * 按照WebSocket规范的要求，处理WebSocket升级握手、PingWebSocketFrame、
                 * PongWebSocketFrame和CloseWebSocketFrame
                 * */
                new WebSocketServerProtocolHandler("/ws"),
                // 处理TextWebSocketFrame和握手完成事件
                new TextWebSocketFrameHandler(group)
        );
    }
}
