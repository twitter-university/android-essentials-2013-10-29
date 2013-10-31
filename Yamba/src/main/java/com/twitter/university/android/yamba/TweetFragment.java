package com.twitter.university.android.yamba;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class TweetFragment extends Fragment {
    private static final String TAG = "TWEET";

    public static class Poster extends AsyncTask<String, Void, Integer> {
        private final Context ctxt;

        public Poster(Context ctxt) { this.ctxt = ctxt; }

        @Override
        protected Integer doInBackground(String... tweets) {
            int ret = R.string.tweet_failed;
            try {
                new YambaClient("student", "password", "http://yamba.marakana.com/api")
                    .postStatus(tweets[0]);
                ret = R.string.tweet_succeeded;
            }
            catch (YambaClientException e) {
                Log.w(TAG, "Post failed", e);
            }

            return Integer.valueOf(ret);
        }

        @Override
        protected void onCancelled() { finish(R.string.tweet_failed); }

        @Override
        protected void onPostExecute(Integer ret) { finish(ret.intValue()); }

        private void finish(int ret) {
            poster = null;
            Toast.makeText(ctxt, ret, Toast.LENGTH_LONG).show();
        }
    }

    private static Poster poster;


    private int okColor;
    private int warnColor;
    private int errColor;

    private int tweetLenMax;
    private int warnMax;
    private int errMax;

    private EditText viewTweet;
    private TextView viewCount;
    private Button buttonSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources rez = getResources();
        okColor = rez.getColor(R.color.green);
        tweetLenMax = rez.getInteger(R.integer.tweet_limit);
        warnColor = rez.getColor(R.color.yellow);
        warnMax = rez.getInteger(R.integer.warn_limit);
        errColor = rez.getColor(R.color.red);
        errMax = rez.getInteger(R.integer.err_limit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweet, container, false);

        viewCount = (TextView) v.findViewById(R.id.tweet_count);

        buttonSubmit = (Button) v.findViewById(R.id.tweet_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { post(); }
        });

        viewTweet = (EditText) v.findViewById(R.id.tweet_tweet);
        viewTweet.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void afterTextChanged(Editable editable) { updateCount(); }

                @Override
                public void beforeTextChanged(CharSequence charSequence, int s, int n, int e) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int s, int n, int e) { }
            } );

        return v;
    }

    // Set the value of the count view to be n, 140 - the length of the text in the tweet view
    // if 140 >= n > 10, count text is green
    // if 10 > n > 0, count text is yellow
    // if 0 > n, count text is red
    // if 140 > n >= 0, button is enabled
    void updateCount() {
        int n = viewTweet.getText().length();

        buttonSubmit.setEnabled(checkTweetLen(n));

        n = tweetLenMax - n;

        int color;
        if (n > warnMax) { color = okColor; }
        else if (n > errMax) { color = warnColor; }
        else  { color = errColor; }

        viewCount.setText(String.valueOf(n));
        viewCount.setTextColor(color);
    }

    // clear the tweet box
    // check for valid tweet
    // handle mashing the submit button
    void post() {
        String tweet = viewTweet.getText().toString();
        if (!checkTweetLen(tweet.length())) { return; }

        if (null != poster) { return; }
        if (BuildConfig.DEBUG) { Log.d(TAG, "posting: " + tweet); }

        poster = new Poster(getActivity().getApplicationContext());
        poster.execute(tweet);
        viewTweet.setText("");
    }

    private boolean checkTweetLen(int n) {
        return (errMax < n) && (tweetLenMax > n);
    }
}
