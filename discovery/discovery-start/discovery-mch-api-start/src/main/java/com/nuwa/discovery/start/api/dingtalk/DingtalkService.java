package com.nuwa.discovery.start.api.dingtalk;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * DingtalkService
 *
 * @author hy
 * @date 2020/8/20 11:09
 * @since 1.0.0
 */
@Slf4j
@Service
public class DingtalkService {

    @Value("${dd.AgentId}")
    private String agentId;

    @Value("${dd.AppKey}")
    private String appKey;

    @Value("${dd.AppSecret}")
    private String appSecret;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getToken() {
        String key = "dd:access_token";
        String accessToken = null;

        String cacheAccessToken = stringRedisTemplate.opsForValue().get(key);

        if (StrUtil.isNotBlank(cacheAccessToken)) {
            return cacheAccessToken;
        }

        String url = "https://oapi.dingtalk.com/gettoken?appkey=" + appKey + "&appsecret=" + appSecret;
        String content = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(content);
        accessToken = jsonObject.getStr("access_token");

        stringRedisTemplate.opsForValue().set(key, accessToken, 6000, TimeUnit.SECONDS);

        return accessToken;
    }

    public String getUserIdByMobile(String mobile) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
            OapiUserGetByMobileRequest req = new OapiUserGetByMobileRequest();
            req.setMobile(mobile);
            req.setHttpMethod("GET");
            OapiUserGetByMobileResponse resp = client.execute(req, getToken());
            log.info("sendTextMessage errCode:{},errMsg:{}", resp.getErrcode(), resp.getErrmsg());
            if (resp.getErrcode().equals(0L)) {
                return resp.getUserid();
            }
        } catch (ApiException e) {
            log.error("DingTalk 发送消息异常.", e);
            return null;
        }
        return null;
    }

    public boolean sendTextMessage(String mobile, String content) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
            OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
            req.setAgentId(Long.parseLong(agentId));
            req.setUseridList((getUserIdByMobile(mobile)));
            req.setToAllUser(false);
            OapiMessageCorpconversationAsyncsendV2Request.Msg msgCorp = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msgCorp.setMsgtype("text");
            OapiMessageCorpconversationAsyncsendV2Request.Text textMessage = new OapiMessageCorpconversationAsyncsendV2Request.Text();
            textMessage.setContent(content);
            msgCorp.setText(textMessage);
            req.setMsg(msgCorp);
            OapiMessageCorpconversationAsyncsendV2Response resp = client.execute(req, getToken());
            log.info("sendTextMessage errCode:{},errMsg:{}", resp.getErrcode(), resp.getErrmsg());
            return resp.getErrcode().equals(0L);
        } catch (ApiException e) {
            log.error("DingTalk 发送消息异常.", e);
            return false;
        }
    }

    public boolean sendMarkDownMessage(String mobile, String content,String title) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
            OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
            req.setAgentId(Long.parseLong(agentId));
            req.setUseridList(getUserIdByMobile(mobile));
            req.setToAllUser(false);
            OapiMessageCorpconversationAsyncsendV2Request.Msg msgCorp = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msgCorp.setMsgtype("markdown");
            OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown = new OapiMessageCorpconversationAsyncsendV2Request.Markdown();
            markdown.setTitle(title);
            markdown.setText(content);
            msgCorp.setMarkdown(markdown);
            req.setMsg(msgCorp);
           // log.info(msgCorp.toString());
            OapiMessageCorpconversationAsyncsendV2Response resp = client.execute(req, getToken());
            log.info("sendMarkDownMessage errCode:{},errMsg:{}", resp.getErrcode(), resp.getErrmsg());
            return resp.getErrcode().equals(0L);
        } catch (ApiException e) {
            log.error("DingTalk 发送消息异常.", e);
            return false;
        }
    }
}
