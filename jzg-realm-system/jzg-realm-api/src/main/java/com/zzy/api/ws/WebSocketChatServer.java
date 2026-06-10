package com.zzy.api.ws;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.zzy.api.client.dto.RoomStatus;
import com.zzy.api.client.dto.RoomChatter;
import com.zzy.api.client.dto.UserChatInfo;
import com.zzy.api.client.mongo.MongoApiClient;
import com.zzy.api.service.eventdepart.IDepartmentInfoService;
import com.zzy.api.service.eventdepart.IDepartmentMemberService;
import com.zzy.api.service.eventdepart.IEventDepartMemberService;
import com.zzy.db.dto.DepartmentInfoMemberInfo;
import com.zzy.db.entity.eventdepart.DepartmentInfo;
import com.zzy.db.entity.eventdepart.DepartmentMember;
import com.zzy.db.entity.eventdepart.EventDepartMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Slf4j
@Component
@ServerEndpoint("/wsChat/{groupName}/{userName}")
public class WebSocketChatServer {


    private static ConcurrentHashMap<String, Session> webSocketSet = new ConcurrentHashMap<>();


    static MongoApiClient<UserChatInfo> userChatMongo;

    static IEventDepartMemberService eventDepartMemberService;

    static IDepartmentInfoService departmentInfoService;


    @Autowired
    public void setUserChatMongo(MongoApiClient<UserChatInfo> mongo) {
        WebSocketChatServer.userChatMongo = mongo;
    }

    @Autowired
    public void setEventDepartMemberService(IEventDepartMemberService eventDepartMemberService) {
        WebSocketChatServer.eventDepartMemberService = eventDepartMemberService;
    }

    @Autowired
    public void setEventDepartMemberService(IDepartmentInfoService departmentInfoService) {
        WebSocketChatServer.departmentInfoService = departmentInfoService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "groupName") String groupName, @PathParam(value = "userName") String userName) {
        if (StrUtil.isBlankOrUndefined(groupName) || StrUtil.isBlankOrUndefined(userName)) {
            return;
        }
        webSocketSet.put(userName + "&" + groupName, session);
        Query q = new Query();
        q.addCriteria(new Criteria().and("groupName").is(groupName)).with(Sort.by(Sort.Order.asc("sendTime")));
        ;
        List<UserChatInfo> list = userChatMongo.findAll(new UserChatInfo(), q);
//        List<EventDepartMember> em = eventDepartMemberService.lambdaQuery().eq(EventDepartMember::getEventId, Integer.parseInt(groupName)).list();
        try {
            webSocketSet.get(userName + "&" + groupName).getBasicRemote().sendText(JSON.toJSONString(list));
            sendRoomStaus(groupName);
        } catch (IOException e) {
            log.error(">>>>>", e);
        }
        log.info("server-connected, current counts:{}", webSocketSet.size());
    }


    @OnClose
    public void onClose(Session session, @PathParam(value = "groupName") String groupName, @PathParam(value = "userName") String userName) {
        webSocketSet.remove(userName + "&" + groupName);
        log.info("server-connection closed,left counts:{}", webSocketSet.size());
        sendRoomStaus(groupName);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("server-onMessage recieve:{}", message);
        if (message.equals("ping")) {
            session.getAsyncRemote().sendText("pong");
            return;
        }
        try {
            if (message.startsWith(ScoketClient.WS_Type)) {
                UserChatInfo uc = JSON.parseObject(message.replace(ScoketClient.WS_Type, ""), UserChatInfo.class);
                sendToGroup(uc);
            }
        } catch (Exception e) {
            log.error("ws-Server-sendMsg-error", e);
        }
    }


    @OnError
    public void onError(Session session, Throwable ex, @PathParam(value = "groupName") String groupName, @PathParam(value = "userName") String userName) {
        webSocketSet.remove(userName + "&" + groupName);
        log.info("onError>>>>活跃人数:{}", webSocketSet.size());
        sendRoomStaus(groupName);
    }


    public void sendToGroup(UserChatInfo uc) {
        Session s = null;
        List<String> keys = webSocketSet.keySet().stream().filter(x -> x.contains("&" + uc.getGroupName())).collect(Collectors.toList());
        try {
            for (String key : keys) {
                s = webSocketSet.get(key);
                if (s == null) {
                    continue;
                } else {
                    s.getBasicRemote().sendText(JSON.toJSONString(uc));
                }
            }
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        System.out.println(DigestUtil.md5Hex16("1041"));
    }

    public void sendRoomStaus(String groupName) {
        if("client".equals(groupName)){
            return;
        }
        try {
            Set<String> keys = webSocketSet.keySet();
            Set<String> newKey = keys.stream().filter(o -> (!StrUtil.isBlankOrUndefined(o) && o.contains("&" + groupName))).collect(Collectors.toSet());
            List<EventDepartMember> ed = eventDepartMemberService.lambdaQuery().eq(EventDepartMember::getEventId, Integer.parseInt(groupName)).list();
            if(ed!=null && ed.size()>0){
                Set<Integer> ids = ed.stream().map(o->o.getDepartMemberId()).collect(Collectors.toSet());
                List<DepartmentInfoMemberInfo> dms = departmentInfoService.getLeaderInfoByMemberId(ids);
                List<RoomChatter> aliveChatter = new ArrayList<>();
                RoomChatter rc = null;
                for (DepartmentInfoMemberInfo member : dms) {
                    rc = new RoomChatter();
                    rc.setMemberName(member.getMemberName());
                    rc.setUserId(member.getLeaderUserId());
                    rc.setLoginName(member.getLeaderLoginName());
                    rc.setIsLive(newKey.contains(DigestUtil.md5Hex16(member.getLeaderUserId().toString())+"&"+groupName));
                    aliveChatter.add(rc);
                }
                Session s = null;
                RoomStatus cri = new RoomStatus();
                cri.setLiveCount(newKey.size());
                cri.setLiveChatter(aliveChatter);
                for (String key : newKey) {
                    s = webSocketSet.get(key);
                    if (s == null) {
                        continue;
                    } else {
                        s.getBasicRemote().sendText("status>>>"+JSON.toJSONString(cri));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}