package com.cnbi.websocket.server;

import com.cnbi.websocket.bean.Client;
import com.cnbi.websocket.bean.MessageBean;
import com.cnbi.websocket.config.WebsocketConfig;
import com.cnbi.websocket.processor.error.ErrorProcessor;
import com.cnbi.websocket.processor.receive.PostReceiveProcessor;
import com.cnbi.websocket.processor.send.PreSendProcessor;
import com.cnbi.websocket.utils.CacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import sun.misc.Cache;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebsocketServer
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/22 15:08
 **/
@Slf4j
@ServerEndpoint("/websocket_server/{username}/{token}")
public class WebsocketServer {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ConcurrentHashMap<String, WebsocketServer> sessionMap;

    @Autowired
    private ApplicationContext applicationContext;

    private final static String errorProcessorSetBeanName = "errorProcessorSet";

    private final static  ObjectMapper objectMapper = new ObjectMapper();

    private Session session;

    private String username;

    private String token;
    @OnOpen
    public void onOpen(@PathParam("username") String username,@PathParam("token") String token, Session session) throws IOException {
        sessionMap.put(username, this);
        this.session = session;
        this.username = username;
        this.token = token;
        String redisToken = ((Client) cacheManager.hGet(cacheManager.AUTH_PREFIX, username)).getToken();
        if(Objects.equals(redisToken, token)){
            cacheManager.hSet(cacheManager.defaultPrefix, username, WebsocketConfig.HTTPURL);
            sendMessage2User(MessageBean.builder().sendType(MessageBean.SendMessageType.LOGIN_SUCCESS).message("认证信息校验成功！").build());
            log.info("用户{}登录成功！token:{},redisToken:{}", username, token, redisToken);
        }else {
            sendMessage2User(MessageBean.builder().sendType(MessageBean.SendMessageType.LOGIN_FAIL).message("认证信息校验未通过！").build());
            log.info("用户{}登录失败！token:{},redisToken:{}", username, token, redisToken);
        }
    }

    @OnClose
    public void onClose(){
        try{
            cacheManager.hDelete(CacheManager.AUTH_PREFIX, username);
            cacheManager.hDelete(CacheManager.defaultPrefix, username);
        } catch (Exception e){
            log.error("移除redis中用户{}信息失败, token:{}！",username, token, e);
        }
        sessionMap.remove(username);
        log.info("用户{}退出成功！", username);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        MessageBean messageBean = objectMapper.readValue(message, MessageBean.class);
        PostReceiveProcessor postReceiveProcessor = (PostReceiveProcessor) applicationContext.getBean(messageBean.getReceiveProcessor());
        postReceiveProcessor.onMessage(messageBean);
    }

    @OnError
    public void onError(Throwable throwable){
        log.error("用户{}socket连接中断！", username);
        sessionMap.remove(username);
        TreeSet<ErrorProcessor> set = (TreeSet<ErrorProcessor>) applicationContext.getBean(errorProcessorSetBeanName);
        set.forEach(errorProcessor -> errorProcessor.processor(username, session, throwable));
    }

    public void sendMessage2User(MessageBean messageBean) throws IOException {
        PreSendProcessor preSendProcessor = (PreSendProcessor) applicationContext.getBean(messageBean.getSendProcessor());
        if(Objects.nonNull(preSendProcessor))
             preSendProcessor.preSend(messageBean);
        String message = objectMapper.writeValueAsString(messageBean);
        session.getBasicRemote().sendText(message);
    }
}