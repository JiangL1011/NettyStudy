package NettyStudy;

import NettyStudy.server.ChatServer;
import io.netty.channel.ChannelFuture;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final int PORT = 8080;

    @Test
    public void startChatServer() {
        final ChatServer endpoint = new ChatServer();
        ChannelFuture future = endpoint.start(new InetSocketAddress(PORT));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

}
