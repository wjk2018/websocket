package com.cnbi.websocket.processor.receive;

import com.cnbi.websocket.bean.MessageBean;

@FunctionalInterface
public interface PostReceiveProcessor {

    void onMessage(MessageBean message);

}
