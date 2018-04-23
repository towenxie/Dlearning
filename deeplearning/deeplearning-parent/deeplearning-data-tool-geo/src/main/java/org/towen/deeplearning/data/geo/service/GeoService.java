package org.towen.deeplearning.data.geo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.towen.deeplearning.data.dao.MetaLocationDao;
import org.towen.deeplearning.data.geo.contant.GeoAppConfig;
import org.towen.deeplearning.data.model.MetaLocationEntity;

import com.alibaba.fastjson.JSONObject;

@Service
public class GeoService {

    private static final String AK = "KFrnL2TGZkU5lsoC2ojYX95D";

    @Resource
    private GeoAppConfig geoAppConfig;
    @Resource
    private MetaLocationDao metaLocationDao;

    public void startUp() {
        List<MetaLocationEntity> metaLocationEntities = metaLocationDao.getMetaLocations();
        if (CollectionUtils.isEmpty(metaLocationEntities)) {
            return;
        }
        updateMetaLocationGeos(metaLocationEntities);
    }

    @Transactional(rollbackFor = Exception.class)
    private void updateMetaLocationGeos(List<MetaLocationEntity> metaLocationEntities) {

        for (MetaLocationEntity metaLocationEntity : metaLocationEntities) {
            Map<String, Double> map = getLngAndLat(metaLocationEntity.getWholeName());
            if (map.size() != 0) {
                metaLocationEntity.setLatitude(map.get("lat"));
                metaLocationEntity.setLongitude(map.get("lng"));
            }
            metaLocationDao.updateMetaLocationGeo(metaLocationEntity);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Map<String, Double> getLngAndLat(String address) {
        Map<String, Double> map = new HashMap<String, Double>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + AK;
        String json = loadJSON(url);
        JSONObject obj = JSONObject.parseObject(json);
        if (obj != null && obj.get("status").toString().equals("0")) {
            double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            map.put("lng", lng);
            map.put("lat", lat);
        } else {
            System.out.println("Cannot find result." + address);
        }
        return map;
    }

    private String loadJSON(String urlString) {
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(urlString);
            Proxy proxy = null;
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
            HttpURLConnection action = (HttpURLConnection) url.openConnection(proxy);
            BufferedReader in = new BufferedReader(new InputStreamReader(action.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
}
