package org.towen.deeplearning.data.geo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.towen.deeplearning.common.util.XmlUtils;
import org.towen.deeplearning.data.dao.MetaLocationDao;
import org.towen.deeplearning.data.geo.contant.GeoAppConfig;
import org.towen.deeplearning.data.geo.contant.LanguageContants;
import org.towen.deeplearning.data.geo.model.LocationModel;
import org.towen.deeplearning.data.geo.model.LocationModel.City;
import org.towen.deeplearning.data.geo.model.LocationModel.CountryRegion;
import org.towen.deeplearning.data.geo.model.LocationModel.Region;
import org.towen.deeplearning.data.geo.model.LocationModel.State;
import org.towen.deeplearning.data.model.MetaLocationEntity;


@Service
public class LocationService {

    private static final String CODE_FORMAT = "%s_%05d";

    @Resource
    private GeoAppConfig geoAppConfig;
    @Resource
    private MetaLocationDao metaLocationDao;

    public void startUp() {
        LocationModel locationModels = XmlUtils.getBean(geoAppConfig.getLocationFile(), LocationModel.class);
        if (locationModels != null) {
            List<MetaLocationEntity> metaLocationEntities = buildMetaLocationEntity(locationModels);
            saveMetaLocationEntities(metaLocationEntities);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void saveMetaLocationEntities(List<MetaLocationEntity> metaLocationEntities) {
        if (CollectionUtils.isEmpty(metaLocationEntities)) {
            return;
        }

        int totalSize = metaLocationEntities.size();
        System.out.println(totalSize);
        int size = geoAppConfig.getLineSize();
        int length = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
        int begin = 0;
        int end = 0;
        for (int i = 0; i < length; i++) {
            begin = size * i + 1;
            if (i != length - 1) {
                end = size * (i + 1);
            } else {
                end = totalSize;
            }
            List<MetaLocationEntity> metaLocationBatch = new ArrayList<MetaLocationEntity>();
            int index = 1;
            for (MetaLocationEntity metaLocationEntity : metaLocationEntities) {
                if (index >= begin && end >= index) {
                    metaLocationBatch.add(metaLocationEntity);
                } else if (end < index) {
                    break;
                }
                index++;
            }
            metaLocationDao.saveMetaLocations(metaLocationBatch);
        }
        metaLocationDao.updateMetaLocationParentCode();
    }

    private List<MetaLocationEntity> buildMetaLocationEntity(LocationModel locationModels) {

        List<MetaLocationEntity> metaLocationEntities = new ArrayList<MetaLocationEntity>();
        for (CountryRegion countryRegion : locationModels.getCountryRegions()) {
            MetaLocationEntity countryEntity = new MetaLocationEntity();
            if (countryRegion.getCode().equals("1")) {
                countryEntity.setCode(LanguageContants.CN_CODE);
            } else {
                countryEntity.setCode(countryRegion.getCode());
            }
            countryEntity.setParentCode(countryEntity.getCode());
            countryEntity.setCountryName(countryRegion.getName());
            countryEntity.setName(countryRegion.getName());
            countryEntity.setWholeName(countryRegion.getName());
            countryEntity.setLevel(1);
            if (StringUtils.isNotBlank(countryEntity.getName())) {
                metaLocationEntities.add(countryEntity);
            }
            if (CollectionUtils.isNotEmpty(countryRegion.getStates())) {
                int stateIndex = 1;
                for (State state : countryRegion.getStates()) {
                    MetaLocationEntity stateEntity = new MetaLocationEntity();
                    stateEntity.setCode(countryEntity.getCode());
                    stateEntity.setName(state.getName());
                    stateEntity.setCountryName(countryEntity.getCountryName());
                    stateEntity.setStateName(state.getName());
                    stateEntity.setWholeName(countryEntity.getWholeName() + state.getName());
                    stateEntity.setLevel(2);
                    stateEntity.setParentCode(countryEntity.getCode());
                    if (StringUtils.isNotBlank(stateEntity.getName())) {
                        stateEntity.setCode(String.format(CODE_FORMAT, countryEntity.getCode(), stateIndex));
                        metaLocationEntities.add(stateEntity);
                    }
                    if (CollectionUtils.isNotEmpty(state.getCities())) {
                        int cityIndex = 1;
                        for (City city : state.getCities()) {
                            MetaLocationEntity cityEntity = new MetaLocationEntity();
                            cityEntity.setCode(String.format(CODE_FORMAT, stateEntity.getCode(), cityIndex));
                            cityEntity.setName(city.getName());
                            cityEntity.setWholeName(stateEntity.getWholeName() + city.getName());
                            cityEntity.setCountryName(stateEntity.getCountryName());
                            cityEntity.setStateName(stateEntity.getStateName());
                            cityEntity.setCityName(city.getName());
                            cityEntity.setLevel(3);
                            cityEntity.setParentCode(stateEntity.getCode());
                            if (StringUtils.isNotBlank(cityEntity.getName())) {
                                metaLocationEntities.add(cityEntity);
                            }
                            if (CollectionUtils.isNotEmpty(city.getRegions())) {
                                int regionIndex = 1;
                                for (Region region : city.getRegions()) {
                                    MetaLocationEntity regionEntity = new MetaLocationEntity();
                                    regionEntity.setCode(String.format(CODE_FORMAT, cityEntity.getCode(), regionIndex));
                                    regionEntity.setName(region.getName());
                                    regionEntity.setWholeName(cityEntity.getWholeName() + region.getName());
                                    regionEntity.setCountryName(cityEntity.getCountryName());
                                    regionEntity.setStateName(cityEntity.getStateName());
                                    regionEntity.setCityName(cityEntity.getCityName());
                                    regionEntity.setRegionName(region.getName());
                                    regionEntity.setLevel(4);
                                    regionEntity.setParentCode(regionEntity.getCode());
                                    metaLocationEntities.add(regionEntity);
                                    regionIndex++;
                                }
                            }
                            cityIndex++;
                        }
                    }
                    stateIndex++;
                }
            }
        }

        return metaLocationEntities;
    }
}
