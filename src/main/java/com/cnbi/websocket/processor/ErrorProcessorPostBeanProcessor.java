package com.cnbi.websocket.processor;

import com.cnbi.websocket.processor.error.ErrorProcessor;
import lombok.extern.java.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @ClassName ErrorProcessorPostBeanProcessor
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/23 16:48
 **/
@Configuration
@Log
public class ErrorProcessorPostBeanProcessor implements BeanPostProcessor {

    private TreeSet<ErrorProcessor> errorProcessorSet = new TreeSet<>((Comparator<? super ErrorProcessor>) (o1, o2) -> o1.getOrder() - o2.getOrder());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ErrorProcessor){
            errorProcessorSet.add((ErrorProcessor) bean);
        }
        return bean;
    }

    @Bean
    public TreeSet<ErrorProcessor> errorProcessorSet(){
        return errorProcessorSet;
    }
}