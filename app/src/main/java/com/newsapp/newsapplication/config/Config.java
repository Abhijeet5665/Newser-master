package com.newsapp.newsapplication.config;

public class Config {
    private static final String APP_NAME = "Newser";
    private static final String APP_NAME_FONT = "fonts/cocogoose.ttf";
    private static final String APP_VERSION = "0.1";

    public static String getVersion() {
        return "v." + APP_VERSION;
    }

    public static String getAppName() {
        return APP_NAME;
    }

    public static String getAppNameFont() {
        return APP_NAME_FONT;
    }
}
