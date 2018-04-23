/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.data.geo.contant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:app.properties")
public class GeoAppConfig {

    @Value("${location.file}")
    private String locationFile;

    @Value("${line.size:500}")
    private int lineSize;


    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public String getLocationFile() {
        return locationFile;
    }

    public void setLocationFile(String locationFile) {
        this.locationFile = locationFile;
    }

}
