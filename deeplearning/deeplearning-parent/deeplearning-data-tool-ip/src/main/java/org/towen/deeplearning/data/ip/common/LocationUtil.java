package org.towen.deeplearning.data.ip.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.towen.deeplearning.common.util.FileUtil;
import org.towen.deeplearning.data.model.MetaIpLocationEntity;

public class LocationUtil {
    @Resource
    private IpAppConfig ipAppConfig;
    private static String filePath = "src/main/resources/province.txt";
    public static List<String> provinceList = new ArrayList<String>();

    public static void analysisLocation(MetaIpLocationEntity entity, String location, String fileProvincePath) {
        filePath = fileProvincePath;
        analysisProvince(entity, location);
        if (!entity.isChina()) {
            return;
        }
        String citys = location.replace(entity.getProvince(), "");
        if (citys == "") {
            return;
        }
        int index = citys.indexOf(IpContants.city_chinese);
        if (index == -1) {
            index = citys.indexOf(IpContants.district_chinese);
        }
        if (index != -1) {
            entity.setCity(citys.substring(0, index + 1));
            if (citys.length() > index) {
                entity.setDistrict(citys.substring(index + 1));
            }
        }
    }

    private static void analysisProvince(MetaIpLocationEntity entity, String location) {
        String result = location;
        entity.setChina(false);
        if (provinceList.size() == 0) {
            provinceList = FileUtil.getMessage(filePath);
        }
        for (String province : provinceList) {
            if (location.contains(province)) {
                result = province;
                entity.setChina(true);
            }
        }
        if (entity.isChina()) {
            entity.setCountry(IpContants.china);
            entity.setProvince(result);
        } else {
            entity.setCountry(location);
        }

    }
}
