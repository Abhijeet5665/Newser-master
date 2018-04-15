package com.newsapp.newsapplication.controllers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

public interface ArticleRenderer {
    /**
     * Renders the articles fetched from https://newsapi.org to the mainLayout
     *
     * @param article the article in JSON object format
     * @param containerLayout the containerLayout
     * @param context the base layout context
     */
    void renderArticle(JSONObject article, LinearLayout containerLayout, Context context);

    /**
     * Returns a CardView object
     *
     * @param context the container layout context
     * @return CardView object
     */
    CardView createArticleCardView(Context context);

    /**
     * Returns a TextView object that will contain the article title
     *
     * @param context the container layout context
     * @param article the article in JSON object format
     * @return TextView object
     */
    TextView createTitleTV(Context context, JSONObject article);

    /**
     * Returns a TextView object that will contain the article description
     *
     * @param context the container layout context
     * @param article the article in JSON object format
     * @return TextView object
     */
    TextView createDescriptionTV(Context context, JSONObject article);

    /**
     * Returns a TextView object that will contain the article HREF
     *
     * @param context the container layout context
     * @param article the article in JSON object format
     * @return TextView object
     */
    TextView createHyperlink(Context context, JSONObject article);

    /**
     * Returns a View object that will contain the article divider
     *
     * @param context the container layout context
     * @return View object
     */
    View createDivider(Context context);

    /**
     * Returns a ImageView object that will contain the article image
     *
     * @param context the container layout context
     * @param article the article in JSON object format
     * @return ImageView object
     */
    ImageView createSourceIV(Context context, JSONObject article);
}
