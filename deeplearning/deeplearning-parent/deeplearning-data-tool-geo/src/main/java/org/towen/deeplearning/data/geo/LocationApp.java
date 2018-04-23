package org.towen.deeplearning.data.geo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.towen.deeplearning.common.util.SpringUtil;
import org.towen.deeplearning.data.geo.service.LocationService;

public class LocationApp {
    static {
        // initialize Spring
        new ClassPathXmlApplicationContext("classpath*:spring.xml");
    }

    public static void main(String[] args) {

        LocationService locationService = SpringUtil.getBean(LocationService.class);
        locationService.startUp();
    }
}
