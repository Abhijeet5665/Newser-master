package com.newsapp.newsapplication.controllers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsapp.newsapplication.logging.Logger;
import com.newsapp.newsapplication.views.ProportionalImageView;
import com.squareup.picasso.Picasso;
import com.newsapp.newsapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public final class ArticleController implements ArticleRenderer, Logger {
    private boolean LastArticle = false;

    public ArticleController(boolean lastArticle) {
        if (lastArticle)
            this.LastArticle = true;
    }

    public void renderArticle(JSONObject article, LinearLayout containerLayout, Context context)  {
        CardView cardView = createArticleCardView(context);
        LinearLayout cardViewLayout = new LinearLayout(cardView.getContext());
        cardViewLayout.setOrientation(LinearLayout.VERTICAL);
        cardViewLayout.setLayoutParams(this.getParams());

        ImageView image = createSourceIV(cardViewLayout.getContext(), article);
        TextView title = createTitleTV(cardViewLayout.getContext(), article);
        TextView description = createDescriptionTV(cardViewLayout.getContext(), article);
        TextView hyperlink = createHyperlink(cardViewLayout.getContext(), article);
        View divider = createDivider(cardViewLayout.getContext());

        if (title != null && description != null && hyperlink != null && image != null) {
            cardViewLayout.addView(image);        // Add the image to the CardViewLayout
            cardViewLayout.addView(title);        // Add the title to the CardViewLayout
            cardViewLayout.addView(description);  // Add the description to the CardViewLayout
            cardViewLayout.addView(divider);      // Add the divider to the CardViewLayout
            cardViewLayout.addView(hyperlink);    // Add the hyperlink to the CardViewLayout
            cardView.addView(cardViewLayout);     // Add the CardViewLayout to the CardView
            containerLayout.addView(cardView);    // Add the CardView to the containerLayout
        }
    }

    public CardView createArticleCardView(Context context) {
        // Create a CardView that will contain the article content
        CardView articleCard = new CardView(context);
        LinearLayout.LayoutParams params = this.getParams();

        int bottomMargin = this.LastArticle ? 60 : 10;
        params.setMargins(0,0,0, bottomMargin);
        articleCard.setLayoutParams(params);
        articleCard.setRadius(9);
        articleCard.setUseCompatPadding(true);

        return articleCard;
    }

    public TextView createTitleTV(Context context, JSONObject article) {
        try {
            // Create a TextView with the articles title as content
            TextView title = new TextView(context);
            LinearLayout.LayoutParams params = this.getParams();
            params.setMargins(20,10,20,10);
            title.setLayoutParams(params);
            title.setText(article.getString("title"));
            title.setTextColor(Color.BLACK);
            title.setTypeface(null, Typeface.BOLD);

            return title;
        } catch(JSONException e) {
            dump(e.getMessage());
        }

        return null;
    }

    public TextView createDescriptionTV(Context context, JSONObject article) {
        try {
            // Create a TextView with the articles title as content
            TextView description = new TextView(context);
            LinearLayout.LayoutParams params = this.getParams();
            params.setMargins(20,0,20,10);
            description.setLayoutParams(params);
            description.setText(this.limitDescriptionContent(article.getString("description")));
            description.setTextColor(Color.BLACK);

            return description;
        } catch (JSONException e) {
            dump(e.getMessage());
        }

        return null;
    }

    public TextView createHyperlink(Context context, JSONObject article) {
        try {
            String link = article.getString("url");
            String href = "<a href='" + link + "'>" + context.getString(R.string.read_more).toUpperCase() + "</a>";

            TextView hyperlink = new TextView(context);
            hyperlink.setText(Html.fromHtml(href));
            hyperlink.setClickable(true);
            hyperlink.setMovementMethod(LinkMovementMethod.getInstance());

            LinearLayout.LayoutParams params = this.getParams();
            params.setMargins(20,20,20,20);

            hyperlink.setLayoutParams(params);
            hyperlink.setLinkTextColor(context.getColor(R.color.colorPrimary));

            return hyperlink;
        } catch (JSONException e) {
            dump(e.getMessage());
        }

        return null;
    }

    public View createDivider(Context context) {
        View divider = new View(context);

        float dividerHeight = context.getResources().getDisplayMetrics().density * 1;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dividerHeight);
        params.setMargins(20,10,20,10); // Set the margins of he divider

        divider.setLayoutParams(params);
        divider.setBackgroundColor(context.getColor(R.color.colorPrimaryDark));
        divider.getBackground().setAlpha(45);
        return divider;
    }

    public ImageView createSourceIV(Context context, JSONObject article) {
        try {
            ProportionalImageView image = new ProportionalImageView(context);
            image.setLayoutParams(this.getParams());
            image.setAdjustViewBounds(true);

            String imageUrl = article.getString("urlToImage");
            Picasso.with(context).load(imageUrl).into(image);

            return image;
        } catch (JSONException e) {
            dump(e.getMessage());
        }

        return null;
    }

    private LinearLayout.LayoutParams getParams() {
        return new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private String limitDescriptionContent(String description) {
        if (description.toCharArray().length > 200)
            return description.substring(0, 200).trim() + "...";
        else
            return  description;
    }

    @Override
    public void dump(String message) {
        final String TAG = Thread.currentThread()
                .getStackTrace()[2]
                .getClassName();

        Log.e(TAG, message);
    }
}

