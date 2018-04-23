package org.towen.deeplearning.data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

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
import org.towen.deeplearning.common.util.IpUtil;
import org.towen.deeplearning.data.contants.Constants;
import org.towen.deeplearning.data.dao.MetaIpLocationDao;
import org.towen.deeplearning.data.model.MetaIpLocationEntity;

@Controller
@RequestMapping("/deeplearning/IP")
public class IpWebAPIController {

    private static final Logger logger = LogManager.getLogger(IpWebAPIController.class);

    @Resource
    private MetaIpLocationDao metaIpLocationDao;

    @RequestMapping(value = "/getMetaIp/ip", method = RequestMethod.POST)
    @ResponseBody
    public void getMetaIp(HttpServletRequest request, HttpServletResponse response)  throws IOException{
        String ip = request.getParameter(Constants.IP);
        if (!StringUtils.isEmpty(ip)) {
            ip = ip.trim();
        } else {
                ip = getIpAddress(request);
        }
        System.out.println(ip);
        String result = "unknow";
        if (checkIP(ip)) {

        try {
            MetaIpLocationEntity metaIpLocationEntity = metaIpLocationDao.getMetaIpLocation(IpUtil.transformIP(ip));
            if (metaIpLocationEntity != null) {
                if (StringUtils.isNotBlank(metaIpLocationEntity.getCountry())) {
                    result = metaIpLocationEntity.getCountry();
                }
                if (StringUtils.isNotBlank(metaIpLocationEntity.getProvince())) {
                    if (StringUtils.isNotBlank(result)) {
                        result += ", ";
                    }
                    result += metaIpLocationEntity.getProvince();
                }
                if (StringUtils.isNotBlank(metaIpLocationEntity.getCity())) {
                    if (StringUtils.isNotBlank(result)) {
                        result += ", ";
                    }
                    result += metaIpLocationEntity.getCity();
                }
                if (StringUtils.isNotBlank(metaIpLocationEntity.getDistrict())) {
                    if (StringUtils.isNotBlank(result)) {
                        result += ", ";
                    }
                    result += metaIpLocationEntity.getDistrict();
                }
                if (StringUtils.isNotBlank(metaIpLocationEntity.getNet())) {
                    if (StringUtils.isNotBlank(result)) {
                        result += ", ";
                    }
                    result += metaIpLocationEntity.getNet();
                }
            }

        } catch (Exception e) {
            logger.error(e);
            result= "Server error";
        }
        
    }else {
        result=ip+"，不是合法IP。";
    }
        request.setCharacterEncoding("utf-8"); //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache"); 
        PrintWriter out = response.getWriter(); 
        out.print(result);
        out.flush();
        out.close();
    }

    private String getIpAddress(HttpServletRequest request) throws IOException {

        String ip = request.getHeader("X-Forwarded-For");
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    private boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }
}
