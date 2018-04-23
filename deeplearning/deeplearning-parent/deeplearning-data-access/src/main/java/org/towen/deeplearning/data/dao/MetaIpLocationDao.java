/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.towen.deeplearning.data.model.MetaIpLocationEntity;

public interface MetaIpLocationDao {

    MetaIpLocationEntity getMetaIpLocation(@Param("ip") long ip);

    void createMetaIpLocationTempTable();

    void saveMetaIpLocationEntity(@Param("metaIpLocationEntities") List<MetaIpLocationEntity> metaIpLocationEntities);

    void createIndex();

    void updateMetaIpLocationTable();

    void updateMetaIpLocationIsEnable();

    void insertMetaIpLocationTable();

    void dropIpTempTable();
}
