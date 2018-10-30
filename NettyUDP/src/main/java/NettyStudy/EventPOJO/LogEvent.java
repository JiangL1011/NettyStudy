package NettyStudy.EventPOJO;

import java.net.InetSocketAddress;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    10/29/2018
 * version: v1.0
 */
public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';
    // 返回发送LogEvent的源的InetSocketAddress
    private final InetSocketAddress source;
    // 返回所发送的LogEvent的日志文件的名称
    private final String logFile;
    // 返回消息内容
    private final String msg;
    // 返回接收LogEvent的时间
    private final long received;

    // 用于传出消息的构造函数
    public LogEvent(String logFile, String msg) {
        this(null, -1, logFile, msg);
    }

    // 用于传入消息的构造函数
    public LogEvent(InetSocketAddress source, long received, String logFile, String msg) {
        this.source = source;
        this.logFile = logFile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceivedTimestamp() {
        return received;
    }
}
