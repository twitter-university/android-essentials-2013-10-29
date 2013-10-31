package com.twitter.university.android.yamba;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.university.android.yamba.service.YambaContract;

// https://www.dropbox.com/sh/d57t65vsmy0wtqh/5rtncIPEZo
// http://bit.ly/1aEEfcJ
public class TweetActivity extends YambaActivity {
    private static final String TAG = "TWEETA";

    private static class PostCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean succeeded = intent.getBooleanExtra(YambaContract.Service.PARAM_POST_SUCCEEDED, false);
            Log.d(TAG, "Posted: " + succeeded);
            Toast.makeText(context, (succeeded) ? R.string.tweet_succeeded : R.string.tweet_failed, Toast.LENGTH_LONG)
                .show();
        }
    }

    private BroadcastReceiver tweetCompleteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CREATE: " + this);

        tweetCompleteReceiver = new PostCompleteReceiver();

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
        registerReceiver(tweetCompleteReceiver, new IntentFilter(YambaContract.Service.ACTION_POST_COMPLETE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "PAUSE");
        unregisterReceiver(tweetCompleteReceiver);
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
