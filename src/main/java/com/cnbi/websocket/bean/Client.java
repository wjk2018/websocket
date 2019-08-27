package com.cnbi.websocket.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Client
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/27 15:18
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private String ip;

    private String hostName;

    private Date loginTime;

    private String username;

    private String truename;


}