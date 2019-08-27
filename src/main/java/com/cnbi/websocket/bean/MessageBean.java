package com.cnbi.websocket.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * @ClassName MassageBean
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/22 16:19
 **/
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageBean {

    @Builder.Default
    private String receiveProcessor = ReceiveMessageType.DEFAULT_TYPE;

    private List<String> receiveUser;

    private String sendUser;

    private String message;

    @Builder.Default
    private String receiveJsonBeanType = ReceiveJsonType.DEFAULT_TYPE;

    @Builder.Default
    private String sendType = SendMessageType.INFO;

    @Builder.Default
    private String sendProcessor = SendProcessor.DEFAULT_TYPE;

    public static class  ReceiveMessageType{
        public final static String DEFAULT_TYPE = "defaultType";
        public final static String HTTP_TYPE = "httpType";
    }

    public static class ReceiveJsonType{
        public final static String DEFAULT_TYPE = "java.lang.String";
        public final static String MAP = "java.util.Map";
        public final static String LIST = "java.util.ArrayList";
    }

    public static  class SendMessageType{
        public final static String INFO = "info";
        public final static String ERROR = "error";
        public final static String CLOSE = "close";
        public final static String LOGIN_SUCCESS = "login_success";
        public final static String LOGIN_FAIL = "login_fail";
    }
    public static class  SendProcessor{
        public final static String DEFAULT_TYPE = "defaultType";
    }
}

