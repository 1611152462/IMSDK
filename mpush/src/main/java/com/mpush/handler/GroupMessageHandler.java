package com.mpush.handler;

import com.mpush.api.ClientListener;
import com.mpush.api.Constants;
import com.mpush.api.Logger;
import com.mpush.api.connection.Connection;
import com.mpush.api.protocol.Packet;
import com.mpush.client.ClientConfig;
import com.mpush.message.AckMessage;
import com.mpush.message.RelationMessage;

/**
 * @ClassName RelationHandler
 * @Description TODO
 * @Author PC
 * @Date 2019/7/30 11:35
 * @Version 1.0
 **/
public class GroupMessageHandler extends BaseMessageHandler<RelationMessage>{
    private final Logger logger = ClientConfig.I.getLogger();
    private final ClientListener listener = ClientConfig.I.getClientListener();


    @Override
    public RelationMessage decode(Packet packet, Connection connection) {
        return new RelationMessage(packet, connection);
    }

    @Override
    public void handle(RelationMessage message) {
        String content=new String(message.content, Constants.UTF_8);
        logger.d(">>> receive relation message=%s", content);
        listener.onReceiveGroupChat(message.getConnection().getClient(),
                content,
                message.bizAck() ? message.getSessionId() : 0);
        if (message.autoAck()) {
            AckMessage.from(message).sendRaw();
            logger.d("<<< send ack for relation messageId=%d", message.getSessionId());
        }
    }
}
