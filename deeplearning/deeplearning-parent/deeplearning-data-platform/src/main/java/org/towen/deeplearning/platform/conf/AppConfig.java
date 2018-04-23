/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.platform.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:app.properties")
public class AppConfig {

    @Value("${job.load.lines:1000}")
    private int jobLoadLines;

    @Value("${job.wait.time:300}")
    private int jobWaitTime;

    @Value("${job.queue.size:1000}")
    private int jobQueueSize;

    public int getJobLoadLines() {
        return jobLoadLines;
    }

    public int getJobWaitTime() {
        return jobWaitTime;
    }

    public int getJobQueueSize() {
        return jobQueueSize;
    }

}
