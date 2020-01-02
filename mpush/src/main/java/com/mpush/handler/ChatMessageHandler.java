package com.mpush.handler;

import com.mpush.api.ClientListener;
import com.mpush.api.Constants;
import com.mpush.api.Logger;
import com.mpush.api.connection.Connection;
import com.mpush.api.protocol.Packet;
import com.mpush.client.ClientConfig;
import com.mpush.message.AckMessage;
import com.mpush.message.ChatMessage;
import com.mpush.message.RelationMessage;
import com.mpush.util.IOUtils;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName ChatMessageHandler
 * @Description 聊天对应的消息处理
 * @Author PC
 * @Date 2019/7/30 16:40
 * @Version 1.0
 **/
public class ChatMessageHandler   extends BaseMessageHandler<ChatMessage>{
    private final static String DEFAULT_CHASET="UTF-8";
    private final Logger logger = ClientConfig.I.getLogger();
    private final ClientListener listener = ClientConfig.I.getClientListener();


    @Override
    public ChatMessage decode(Packet packet, Connection connection) {
        return new ChatMessage(packet, connection);
    }

    @Override
    public void handle(ChatMessage message) {
        String content = new String(message.content, Constants.UTF_8);
        logger.d(">>> receive chat message=%s", content);
        listener.onReceiveChat(message.getConnection().getClient(),
                content,
                message.bizAck() ? message.getSessionId() : 0);
        if (message.autoAck()) {
            AckMessage.from(message).sendRaw();
            logger.d("<<< send ack for chat messageId=%d", message.getSessionId());
        }
    }
}
