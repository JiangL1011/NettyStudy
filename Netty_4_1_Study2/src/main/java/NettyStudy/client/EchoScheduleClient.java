package NettyStudy.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018年11月19日
 * version: v1.0
 */
public class EchoScheduleClient {
    public void start(String host, int port) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            final Channel channel = future.channel();

            ScheduledFuture<?> scheduledFuture = channel.eventLoop().scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    channel.writeAndFlush(Unpooled.copiedBuffer(String.valueOf(System.currentTimeMillis()).getBytes(CharsetUtil.UTF_8)));
                }
            }, 1, 2, TimeUnit.SECONDS);
            // 取消定时任务
            Thread.sleep(5000);
            scheduledFuture.cancel(false);

            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) {
        try {
            new EchoScheduleClient().start("localhost", 8080);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
