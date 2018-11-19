package NettyStudy.start;

import NettyStudy.client.EchoClient;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    2018年11月19日
 * version: v1.0
 */
public class StartClient3 {
    public static void main(String[] args) {
        try {
            new EchoClient().start(StartConstant.HOST, StartConstant.PORT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
