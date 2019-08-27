package com.cnbi.websocket.processor.error;

import org.springframework.core.Ordered;

import javax.websocket.Session;

public interface ErrorProcessor extends Ordered {

    void processor(String username, Session session, Throwable throwable);

}
