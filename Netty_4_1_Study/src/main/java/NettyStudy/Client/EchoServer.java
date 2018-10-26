package NettyStudy.Client;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    10/24/2018
 * version: v1.0
 */
public class EchoServer {
    public void start(int port) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture future = b.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        }
    }

    private class EchoServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("Echo服务器接收到消息: " + buf.toString(CharsetUtil.UTF_8));
            ctx.write(Unpooled.copiedBuffer("消息已送达！".getBytes(CharsetUtil.UTF_8)));
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
