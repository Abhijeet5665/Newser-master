package com.newsapp.newsapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsapp.newsapplication.config.Config;
import com.newsapp.newsapplication.controllers.NewsApiController;
import com.newsapp.newsapplication.logging.Logger;

public class SplashActivity extends AppCompatActivity implements Logger {
    public final int SPLASH_DISPLAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the current news source to the default source. TODO: Make this configurable through the app
        NewsApiController.setCurrentNewsSource(NewsApiController.HOME_NEWS_SOURCE);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(this.getBaseContext(), R.color.colorPrimary)); // Set background color
        this.setContentView(R.layout.activity_splash);

        this.displayAppName(); // Print the application name

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent feedActivity = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(feedActivity);
                SplashActivity.this.finish();
            }
        }, this.SPLASH_DISPLAY_TIME);
    }

    private void displayAppName() {
        TextView t = this.findViewById(R.id.SplashTitle);
        Typeface font = Typeface.createFromAsset(getAssets(), Config.getAppNameFont());
        final String appName = Config.getAppName().toUpperCase();

        t.setText(appName);
        t.setTypeface(font);
    }

    @Override
    public void dump(String message) {
        final String TAG = Thread.currentThread()
                .getStackTrace()[2]
                .getClassName();

        Log.e(TAG, message);
    }
}
