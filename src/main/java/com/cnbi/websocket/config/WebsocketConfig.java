package com.cnbi.websocket.config;

import com.cnbi.websocket.server.WebsocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebsocketConfig
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/22 15:06
 **/
@Configuration
public class WebsocketConfig {

        public static final String HTTPURL;

        private static final String url = "/send_message";

        static{
            String localHost = null;
            try {
                localHost = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objectNames = null;
            try {
                objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                        Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            }
            String port = objectNames.iterator().next().getKeyProperty("port");
            StringBuilder builder = new StringBuilder("http://");
            HTTPURL = builder.append(localHost).append(":").append(port).append(url).toString();
        }
        @Bean
        public ServerEndpointExporter serverEndpointExporter(){
            return new ServerEndpointExporter();
        }

        @Bean
        public ConcurrentHashMap<String, WebsocketServer> websocketServerMap(){
            return new ConcurrentHashMap<>();
        }

}