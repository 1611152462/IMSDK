package com.mpush.message;

import com.mpush.api.connection.Connection;
import com.mpush.api.protocol.Command;
import com.mpush.api.protocol.Packet;

/**
 * @ClassName ChatMessage
 * @Description IM聊天消息
 * @Author PC
 * @Date 2019/7/30 16:42
 * @Version 1.0
 **/
public class ChatMessage  extends BaseMessage{

    public byte[] content;

    public ChatMessage(byte[] content, Connection connection) {
        super(new Packet(Command.CHAT, genSessionId()), connection);
        this.content = content;
    }
    public ChatMessage(Packet packet, Connection connection) {
        super(packet, connection);
    }

    @Override
    public void decode(byte[] body) {
        content = body;
    }

    @Override
    public byte[] encode() {
        return content;
    }

    public boolean autoAck() {
        return packet.hasFlag(Packet.FLAG_AUTO_ACK);
    }

    public boolean bizAck() {
        return packet.hasFlag(Packet.FLAG_BIZ_ACK);
    }

    public ChatMessage addFlag(byte flag) {
        packet.addFlag(flag);
        return this;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content.length + '\'' +
                '}';
    }
}
