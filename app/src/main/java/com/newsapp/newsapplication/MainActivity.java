package com.newsapp.newsapplication;

import com.newsapp.newsapplication.controllers.ArticleController;
import com.newsapp.newsapplication.controllers.DrawerController;
import com.newsapp.newsapplication.controllers.NewsApiController;
import com.newsapp.newsapplication.logging.Logger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Logger {
    public JSONArray Articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        if (this.hasConnection()) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(NewsApiController.mapSourceToString(NewsApiController.getCurrentSource()).replace('-', ' '));
            setSupportActionBar(toolbar);

            // Create the side menu (MaterialDrawer)
            DrawerController dw = new DrawerController();
            dw.createMaterialDrawer(toolbar, this);

            // Retrieve the data
            this.fetchNewsArticles();
        }
    }

    private boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }

    public LinearLayout getLayout() {
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.mainLayout);
        layout.setOrientation(LinearLayout.VERTICAL);

        ScrollView.LayoutParams params = new ScrollView.LayoutParams(
            ScrollView.LayoutParams.MATCH_PARENT,
            ScrollView.LayoutParams.WRAP_CONTENT
        );

        layout.setLayoutParams(params);

        LinearLayout containerLayout;
        if (findViewById(R.id.containerLayout) == null) {
            containerLayout = new LinearLayout(layout.getContext());
            containerLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );

            containerParams.setMargins(20,20,20,0);
            containerLayout.setLayoutParams(containerParams);
            containerLayout.setId(R.id.containerLayout);
            layout.addView(containerLayout);
        } else {
            containerLayout = (LinearLayout) findViewById(R.id.containerLayout);
        }

        return containerLayout;
    }

    public void renderArticles() {
        if (Articles.length() > 0) {
            LinearLayout layout = this.getLayout(); // Get the layout

            for (int i = 0; i < Articles.length(); i++) {
                try {
                    JSONObject article = Articles.getJSONObject(i);
                    boolean lastArticle = i == (Articles.length() - 1);

                    ArticleController ar = new ArticleController(lastArticle);
                    ar.renderArticle(article, layout, getApplicationContext()); // Render the article
                } catch (JSONException e) {
                    dump(e.getMessage());
                }
            }
        }
    }

    public class NewsapiRepository extends AsyncTask<Void, Void, String> {
        private final String TAG = "NEWSAPI";

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL(NewsApiController.getFullApiUrl());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e){
                dump(e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                dump(getString(R.string.api_error));
            } else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    Articles = object.getJSONArray("articles");

                    dump(Articles.toString());
                    renderArticles(); // Print all the articles
                } catch (JSONException e) {
                    dump(e.getMessage());
                }
            }
        }
    }

    public void fetchNewsArticles() {
        new NewsapiRepository().execute();
    }

    @Override
    public void dump(String message) {
        final String TAG = Thread.currentThread()
                .getStackTrace()[2]
                .getClassName();

        Log.e(TAG, message);
    }
}
