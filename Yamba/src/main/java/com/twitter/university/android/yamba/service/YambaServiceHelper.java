package com.twitter.university.android.yamba.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by bmeike on 10/31/13.
 */
public class YambaServiceHelper {
    private static final String TAG = "HELPER";

    public static void post(Context ctxt, String tweet) {
        Log.d(TAG, "Posting: " + tweet);
        Intent call = new Intent(YambaContract.Service.ACTION_EXECUTE);
        call.putExtra(YambaContract.Service.PARAM_OP, YambaContract.Service.OP_POST);
        call.putExtra(YambaContract.Service.PARAM_TWEET, tweet);
        ctxt.startService(call);
    }
}
