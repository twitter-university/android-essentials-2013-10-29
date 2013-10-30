package com.twitter.university.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// https://www.dropbox.com/sh/d57t65vsmy0wtqh/5rtncIPEZo
// http://bit.ly/1aEEfcJ
public class TweetActivity extends Activity {
    private static final String TAG = "TWEETA";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CREATE: " + this);

        setContentView(R.layout.activity_tweet);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.tweet_container, new TweetFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "STOP");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DESTROY");
    }

}
