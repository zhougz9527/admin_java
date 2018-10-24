package com.example.admin_java.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @Author: Think
 * @Date: 2018/10/11
 * @Time: 17:54
 */
@ClientEndpoint
public class WebSocketClientEndPoint {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketClientEndPoint.class);

    private Session userSession = null;
    private MessageHandler messageHandler;

    public boolean sessionFinish = false;


    public WebSocketClientEndPoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        logger.info("opening websocket ...");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason closeReason) {
        logger.info("closing websocket...");
        this.userSession = null;
        synchronized (this) {
            this.sessionFinish = true;
            this.notifyAll();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public boolean isSessionFinish() {
        return sessionFinish;
    }

    public void addMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void snedTextMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }


    public void sendBinaryMessage(ByteBuffer data) {
        this.userSession.getAsyncRemote().sendBinary(data);
    }


    public static interface MessageHandler {
        public void handleMessage(String message);
    }



}
