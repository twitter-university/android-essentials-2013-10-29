package com.twitter.university.android.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaContract;


public class TimelineFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int TIMELINE_LOADER = 42;

    private static final String[] FROM = new String[] {
        YambaContract.Timeline.Columns.HANDLE,
        YambaContract.Timeline.Columns.TWEET,
        YambaContract.Timeline.Columns.TIMESTAMP
    };

    private static final int[] TO = new int[] {
        R.id.timeline_handle,
        R.id.timeline_tweet,
        R.id.timeline_timestamp
    };

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        return new CursorLoader(
            getActivity(),
            YambaContract.Timeline.URI,
            null,
            null,
            null,
            YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        ((SimpleCursorAdapter) getListView().getAdapter()).swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((SimpleCursorAdapter) getListView().getAdapter()).swapCursor(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.timeline_row, null, FROM, TO, 0);
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);

        return rootView;
    }
}
