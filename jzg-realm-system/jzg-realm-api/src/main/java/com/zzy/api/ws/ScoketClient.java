package com.zzy.api.ws;


import com.alibaba.fastjson.JSON;
import com.zzy.api.client.dto.UserChatInfo;
import com.zzy.api.client.mongo.MongoApiClient;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


@Component
public class ScoketClient {

    public static final String WS_Type = "apiClient=>";

    @Autowired
    MongoApiClient<UserChatInfo> userChatMongo;

    @Value("${ws.client.url}")
    private String wsUrl;


    public void sendChatInfo(UserChatInfo uc){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxSessionIdleTimeout(65000);
        container.setAsyncSendTimeout(3000);
        WsClient client = new WsClient();
        try {
            container.connectToServer(client, new URI(wsUrl));
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.send(WS_Type+ JSON.toJSONString(uc));
        userChatMongo.insertOne(uc);
    }

}
