package NettyStudy;

import NettyStudy.Broadcast.LogEventBroadcaster;
import NettyStudy.monitor.LogEventMonitor;
import io.netty.channel.Channel;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static File logFile = new File(
            "C:\\Users\\JiangL\\Documents\\workspace\\NettyStudy\\NettyUDP\\src\\main\\java\\NettyStudy\\testLog.log");

    @Test
    public void startUDP() {
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(new InetSocketAddress(HOST, PORT), logFile);
        try {
            broadcaster.run();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            broadcaster.stop();
        }
    }

    @Test
    public void startUDPMonitor() {
        LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(PORT));
        Channel channel = monitor.bind();
        System.out.println("LogEventMonitor running...");
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            monitor.stop();
        }
    }
}
