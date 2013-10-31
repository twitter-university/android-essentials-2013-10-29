package com.twitter.university.android.yamba.svc;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.twitter.university.android.yamba.R;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;


public class YambaService extends IntentService {
    private static final String TAG = "SVC";

    private static final int OP_POST_COMPLETE = -1;
    private static final String PARAM_TWEET = "YambaService.TWEET";

    private static class PostCompleteHandler extends Handler {
        private final Context ctxt;

        public PostCompleteHandler(Context ctxt) { this.ctxt = ctxt; }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OP_POST_COMPLETE:
                    Log.d(TAG, "sent: " + (msg.arg1 == R.string.tweet_succeeded));
                    Toast.makeText(ctxt, msg.arg1, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public static void post(Context ctxt, String tweet) {
        Log.d(TAG, "post: " + tweet);
        Intent call = new Intent(ctxt, YambaService.class);
        call.putExtra(PARAM_TWEET, tweet);
        ctxt.startService(call);
    }


    private volatile PostCompleteHandler hdlr;
    private volatile YambaClient client;

    public YambaService() { super(TAG); }

    @Override
    public void onCreate() {
        super.onCreate();
        hdlr = new PostCompleteHandler(this);
        client = new YambaClient("student", "password", "http://yamba.marakana.com/api");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String tweet = intent.getStringExtra(PARAM_TWEET);
        Log.d(TAG, "sending: " + tweet);

        int ret = R.string.tweet_failed;
        try {
            client.postStatus(tweet);
            ret = R.string.tweet_succeeded;
        }
        catch (YambaClientException e) {
            Log.w(TAG, "Post failed", e);
        }

        Message.obtain(hdlr, OP_POST_COMPLETE, ret, 0).sendToTarget();
    }
}
