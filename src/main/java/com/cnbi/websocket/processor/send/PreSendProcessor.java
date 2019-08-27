package com.cnbi.websocket.processor.send;

import com.cnbi.websocket.bean.MessageBean;

@FunctionalInterface
public interface PreSendProcessor {

    void preSend(MessageBean message);

}
