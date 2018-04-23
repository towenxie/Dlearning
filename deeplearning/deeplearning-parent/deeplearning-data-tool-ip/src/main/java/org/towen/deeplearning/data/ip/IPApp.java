package org.towen.deeplearning.data.ip;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.towen.deeplearning.common.util.SpringUtil;
import org.towen.deeplearning.data.ip.service.IpService;

public class IPApp {
    static {
        // initialize Spring
        new ClassPathXmlApplicationContext("classpath*:spring.xml");
    }

    public static void main(String[] args) {
        IpService ipService = SpringUtil.getBean(IpService.class);
        ipService.startUp();
    }
}
