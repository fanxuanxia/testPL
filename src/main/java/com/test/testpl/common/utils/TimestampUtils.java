package com.test.testpl.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtils {
    public static Date convertTimestampToDate(long timestamp) {
        return new  Date(timestamp);
    }
}
