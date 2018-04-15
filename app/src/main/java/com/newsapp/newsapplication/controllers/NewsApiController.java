package com.newsapp.newsapplication.controllers;

import android.content.Context;

public class NewsApiController {
    private static NewsSource currentSource;
    private Context context;

    private static final String API_URL = "https://newsapi.org/v2/top-headlines?sources=";
    private static final String API_KEY = "c7a7729e84894fc1b377ae4c3d27df55";
    public static final NewsSource HOME_NEWS_SOURCE = NewsSource.RTL_NIEUWS;

    public static void setCurrentNewsSource(NewsSource source) {
        currentSource = source;
    }

    public static NewsSource getCurrentSource() {
        return currentSource;
    }

    public static String mapSourceToString(NewsSource source) {
        // TODO: Use the resources instead of hard coded strings
        switch (source) {
            case RTL_NIEUWS:
                return "RTL-Nieuws";
            case POLYGON:
                return "Polygon";
            case THE_VERGE:
                return "The-Verge";
            case THE_GUARDIAN:
                return "The-Guardian-UK";
            case CNN:
                return "CNN";
            case NATIONAL_GEOGRAPHIC:
                return "National-Geographic";
            case TECH_RADAR:
                return "TechRadar";
            case TECH_CRUNCH:
                return "TechCrunch";
            case NEW_YORK_TIMES:
                return "The-New-York-Times";
            case IGN:
                return "IGN";
            default:
                return "RTL-Nieuws";
        }
    }

    public static String getFullApiUrl() {
        return API_URL + mapSourceToString(currentSource).toLowerCase() + "&apiKey=" + API_KEY;
    }

    public enum NewsSource {
        RTL_NIEUWS,
        THE_GUARDIAN,
        NATIONAL_GEOGRAPHIC,
        NEW_YORK_TIMES,
        CNN,
        THE_VERGE,
        TECH_RADAR,
        TECH_CRUNCH,
        POLYGON,
        IGN;

        public static int getIndex(NewsSource source) {
            int index = 0;

            for (int i = 0; i < NewsSource.values().length; i++) {
                if (NewsSource.values()[i] == source) {
                    index = i;
                }
            }

            return index;
        }
    }
}
