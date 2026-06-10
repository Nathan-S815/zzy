package com.zzy.api.ws;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
@Slf4j
public class WsClient {

    private static final String LogPreFix = ">>>>>>>>>>";

    private Session session;

    @OnOpen
    public void open(Session session){
        log.info(LogPreFix+"client-opening");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message){
        log.info(LogPreFix+"client-recieve:{}",message);
    }

    @OnClose
    public void onClose(){
        log.info(LogPreFix+"client-close:{}");
    }

    /**
     * 发送客户端消息到服务端
     * @param message 消息内容
     */
    public void send(String message){
        this.session.getAsyncRemote().sendText(message);
    }

}
