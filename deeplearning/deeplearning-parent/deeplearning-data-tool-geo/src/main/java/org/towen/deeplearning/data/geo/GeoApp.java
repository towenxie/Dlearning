package org.towen.deeplearning.data.geo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.towen.deeplearning.common.util.SpringUtil;
import org.towen.deeplearning.data.geo.service.GeoService;

public class GeoApp {
    static {
        // initialize Spring
        new ClassPathXmlApplicationContext("classpath*:spring.xml");
    }

    public static void main(String[] args) {

        GeoService geoService = SpringUtil.getBean(GeoService.class);
        geoService.startUp();
    }
}
