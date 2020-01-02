package com.mpush.message;

import com.mpush.api.connection.Connection;
import com.mpush.api.protocol.Command;
import com.mpush.api.protocol.Packet;
import com.mpush.util.ByteBuf;

import java.nio.ByteBuffer;

/**
 * @ClassName RelationMessage
 * @Description 关系消息实体
 * @Author PC
 * @Date 2019/7/30 11:39
 * @Version 1.0
 **/
public class RelationMessage extends BaseMessage{

    public byte[] content;

    public RelationMessage(byte[] content, Connection connection) {
        super(new Packet(Command.RELATION, genSessionId()), connection);
        this.content = content;
    }
    public RelationMessage(Packet packet, Connection connection) {
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

    public RelationMessage addFlag(byte flag) {
        packet.addFlag(flag);
        return this;
    }

    @Override
    public String toString() {
        return "RelationMessage{" +
                "content='" + content.length + '\'' +
                '}';
    }


}
