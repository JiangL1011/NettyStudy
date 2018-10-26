package NettyStudy;

import NettyStudy.Server.EchoServer;
import NettyStudy.Client.EchoClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    @Test
    public void runServer() {
        try {
            new EchoServer().start(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runClient() {
        try {
            new EchoClient().start(HOST, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void releaseTest(){
        ByteBuf buf = Unpooled.copiedBuffer("aaa".getBytes(CharsetUtil.UTF_8));
        System.out.println(buf.toString(CharsetUtil.UTF_8));
        System.out.println(buf.release());
        System.out.println(buf.refCnt());
        System.out.println(buf.toString(CharsetUtil.UTF_8));
    }
}
