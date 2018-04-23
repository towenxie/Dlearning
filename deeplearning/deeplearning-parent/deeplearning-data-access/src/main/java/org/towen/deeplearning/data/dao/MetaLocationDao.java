/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.towen.deeplearning.data.model.MetaLocationEntity;

public interface MetaLocationDao {

    List<MetaLocationEntity> getMetaLocations();

    void saveMetaLocations(@Param("metaLocationEntities") List<MetaLocationEntity> metaLocationEntities);

    void updateMetaLocationParentCode();

    void updateMetaLocationGeo(MetaLocationEntity metaLocationEntity);

    List<Long> getMetaLocationId(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
            @Param("minLng") double minLng, @Param("maxLng") double maxLng);

    Long getLocationIdByGeo(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
