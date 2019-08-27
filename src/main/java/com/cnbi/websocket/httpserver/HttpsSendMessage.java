package com.cnbi.websocket.httpserver;

import com.cnbi.websocket.bean.MessageBean;
import com.cnbi.websocket.server.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName HttpsSendMessage
 * @Description 业务系统发送消息调用接口
 * @Author Wangjunkai
 * @Date 2019/8/27 15:39
 **/
@Controller
@Slf4j
public class HttpsSendMessage {
    @Autowired
    private ConcurrentHashMap<String, WebsocketServer> sessionMap;

    public void sendMessage(@RequestBody MessageBean messageBean){
        List<String> receiveUser = messageBean.getReceiveUser();
        String sendUser = messageBean.getSendUser();
        String message = messageBean.getMessage();
        if(Objects.nonNull(receiveUser) && Objects.nonNull(sendUser) && Objects.nonNull(sendUser) && Objects.nonNull(message)){
            receiveUser.parallelStream().forEach(u -> {
                try {
                    sessionMap.get(u).sendMessage2User(messageBean);
                } catch (IOException e) {
                    log.error("用户{}向用户{}发送：{} 失败！", messageBean.getSendUser(), u, e);
                }
            });
        }else{
            log.error("用户{}向用户{}发送：{} 失败！", messageBean.getSendUser(), messageBean.getReceiveUser());
        }
    }

}