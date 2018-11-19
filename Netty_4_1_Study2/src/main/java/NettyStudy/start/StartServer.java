package NettyStudy.start;

import NettyStudy.server.EchoServer;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018年11月19日
 * version: v1.0
 */
public class StartServer {
    public static void main(String[] args) {
        try {
            EchoServer.start(StartConstant.PORT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
