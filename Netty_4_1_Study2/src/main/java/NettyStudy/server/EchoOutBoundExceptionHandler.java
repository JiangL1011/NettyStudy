package NettyStudy.server;

import io.netty.channel.*;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018年11月21日
 * version: v1.0
 */
public class EchoOutBoundExceptionHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                    future.channel().close();
                }
            }
        });
    }
}
