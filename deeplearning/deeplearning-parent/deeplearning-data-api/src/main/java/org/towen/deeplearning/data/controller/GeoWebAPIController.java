package org.towen.deeplearning.data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.towen.deeplearning.data.contants.Constants;
import org.towen.deeplearning.data.dao.MetaLocationDao;

@Controller
@RequestMapping("/deeplearning/GEO")
public class GeoWebAPIController {

    private static final Logger logger = LogManager.getLogger(GeoWebAPIController.class);

    @Resource
    private MetaLocationDao metaLocationDao;

    @RequestMapping(value = "/getLocationGeo/ip", method = RequestMethod.POST)
    @ResponseBody
    public void getLocationGeo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String location = request.getParameter(Constants.LOCATION);
        String result = "unknow";
        if (StringUtils.isEmpty(location)) {
            return;
        }
        location = location.trim();
        System.out.println(location);
        String[] geos = location.split(",");
        if (geos.length != 2) {
            return;
        }
        double latitude = Double.parseDouble(geos[0]);
        double longitude = Double.parseDouble(geos[1]);
        try {
            Long geoLocationId = metaLocationDao.getLocationIdByGeo(latitude, longitude);
            result = geoLocationId.toString();
        } catch (Exception e) {
            logger.error(e);
            result = "Server error";
        }
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }
}
