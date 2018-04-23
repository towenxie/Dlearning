/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.data.ip.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:app.properties")
public class IpAppConfig {

    @Value("${ip.datasource}")
    private String dataSource;

    @Value("${china.province}")
    private String province;

    @Value("${line.size:500}")
    private int lineSize;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
