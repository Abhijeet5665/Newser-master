package com.newsapp.newsapplication.logging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public interface Logger  {
    /**
     * Logs a message
     * @param message the message that's gonna be dumped
     */
    void dump(String message);
}
