package NettyStudy.monitor;

import NettyStudy.EventPOJO.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * description:
 * author:  JiangL
 * company:
 * date:    10/30/2018
 * version: v1.0
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) {
        // 获取对DatagramPacket中的数据(ByteBuf)的引用
        ByteBuf data = datagramPacket.content();

        // 获取SEPARATOR索引
        int idx = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);

        // 提取文件名
        String fileName = data.slice(0, idx).toString(CharsetUtil.UTF_8);

        // 提取日志消息
        String logMsg = data.slice(idx + 1, data.readableBytes()).toString(CharsetUtil.UTF_8);

        LogEvent event = new LogEvent(datagramPacket.sender(), System.currentTimeMillis(), fileName, logMsg);

        out.add(event);
    }
}
