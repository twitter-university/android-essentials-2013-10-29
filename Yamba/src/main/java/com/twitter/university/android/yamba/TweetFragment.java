package com.twitter.university.android.yamba;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TweetFragment extends Fragment {
    private static final String TAG = "TWEET";

    private EditText viewTweet;
    private TextView viewCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweet, container, false);

        viewCount = (TextView) v.findViewById(R.id.tweet_count);
        viewTweet = (EditText) v.findViewById(R.id.tweet_tweet);

        viewTweet.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void afterTextChanged(Editable editable) { setCount(); }

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
    void setCount() {
        Log.d(TAG, "Count: " + viewTweet.getText().length());
    }
}
