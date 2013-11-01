package com.twitter.university.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TimelineActivity extends YambaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                .add(R.id.timeline_container, new TimelineFragment())
                .commit();
        }
    }
}
