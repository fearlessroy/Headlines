package com.wyf.util;

/**
 * Created by w7397 on 2017/3/22.
 */
public class RedisKeyUtil {
    private static String SPILT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }

    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPILT + String.valueOf(entityType) + SPILT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPILT + String.valueOf(entityType) + SPILT + String.valueOf(entityId);
    }
}
