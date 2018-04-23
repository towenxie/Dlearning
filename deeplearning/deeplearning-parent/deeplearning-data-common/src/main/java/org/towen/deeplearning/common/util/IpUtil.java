/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.common.util;

public class IpUtil {

    public static long transformIP(String ip) {
        long result = 0;
        try {
            String[] split = ip.split("\\.");
            if (split.length != 4)
                return result;
            result |= (Long.parseLong(split[3]) & 0xFF);
            result |= ((Long.parseLong(split[2]) << 8) & 0xFF00);
            result |= ((Long.parseLong(split[1]) << 16) & 0xFF0000);
            result |= ((Long.parseLong(split[0]) << 24) & 0xFF000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
