package NettyStudy;

import NettyStudy.Client.EchoServer;
import NettyStudy.Server.EchoClient;
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
}
