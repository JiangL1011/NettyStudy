package NettyStudy;

import NettyStudy.secure.SecureChatServer;
import NettyStudy.server.ChatServer;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.junit.Test;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

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

    @Test
    public void startChatServerAsSecureModel() {
        SslContext context = null;
        try {
            SelfSignedCertificate cert = new SelfSignedCertificate();
            context = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();
        } catch (CertificateException | SSLException e) {
            e.printStackTrace();
        }

        final SecureChatServer endpoint = new SecureChatServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(PORT));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

    @Test
    public void test() {
        String msg = "474554202f777320485454502f312e310d0a486f73743a206c6f63616c" +
                "686f73743a383038300d0a436f6e6e656374696f6e3a2055706772616465" +
                "0d0a507261676d613a206e6f2d63616368650d0a43616368652d436f6e74" +
                "726f6c3a206e6f2d63616368650d0a557365722d4167656e743a204d6f7a" +
                "696c6c612f352e30202857696e646f7773204e542031302e303b20574f57" +
                "363429204170706c655765624b69742f3533372e333620284b48544d4c2c" +
                "206c696b65204765636b6f29204368726f6d652f37302e302e333533382e" +
                "3737205361666172692f3533372e33360d0a557067726164653a20776562" +
                "736f636b65740d0a4f726967696e3a2066696c653a2f2f0d0a5365632d57" +
                "6562536f636b65742d56657273696f6e3a2031330d0a4163636570742d45" +
                "6e636f64696e673a20677a69702c206465666c6174652c2062720d0a4163" +
                "636570742d4c616e67756167653a20656e2c7a682d434e3b713d302e392c" +
                "7a683b713d302e380d0a5365632d576562536f636b65742d4b65793a207a" +
                "426e62582f4b4275667570336a75497a64475742413d3d0d0a5365632d57" +
                "6562536f636b65742d457874656e73696f6e733a207065726d6573736167" +
                "652d6465666c6174653b20636c69656e745f6d61785f77696e646f775f62" +
                "6974730d0a0d0a";
        byte[] bytes = ByteBufUtil.decodeHexDump(msg);
        System.out.println(new String(bytes));

    }

}
