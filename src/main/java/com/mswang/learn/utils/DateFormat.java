package com.mswang.learn.utils;

public enum DateFormat {

	DATE("yyyy-MM-dd", "日期"),
    DATETIME24("yyyy-MM-dd HH:mm:ss", "24小时制时间"),
    GMT_DATETIME("yyyy-MM-ddTHH:mm:ssZ", "GMT时间"),
    COMPRESS_DATETIME("yyyyMMddHHmmss", "压缩24小时制时间");

    private final String format;
    private final String name;

    private DateFormat(String format, String name) {
        this.format = format;
        this.name = name;
    }

    public final String getFormat() {
        return this.format;
    }

    public final String getName() {
        return this.name;
    }
}
