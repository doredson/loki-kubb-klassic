package net.oredson.loki.kubb.fragments;

import net.oredson.loki.kubb.R;
import net.oredson.loki.kubb.activities.EditTeamActivity;
import net.oredson.loki.kubb.db.TeamTable;
import net.oredson.loki.kubb.providers.KubbContentProvider;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TeamListFragment extends ListFragment implements LoaderCallbacks<Cursor> {
	boolean mDualPane;
	int mCurCheckPosition = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			// Restore last state for checked position.
			mCurCheckPosition = savedInstanceState.getInt("curChoice", -1);
		}

		if (mDualPane) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(mCurCheckPosition);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);

		// The desired columns to be bound
		String[] columns = new String[] {
				TeamTable.COLUMN_NAME,
				TeamTable.COLUMN_LOCATION
		};

		// the XML defined views which the data will be bound to
		int[] to = new int[] {
				R.id.team_name,
				R.id.location,
		};

		setListAdapter(new SimpleCursorAdapter(
				getActivity(),
				R.layout.team_list_detail,
				null,
				columns,
				to,
				0));

		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = getActivity().findViewById(R.id.details);
		mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_actions, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.add:
			showDetails(-1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetails(position);
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a
	 * whole new activity in which it is displayed.
	 */
	void showDetails(int index) {
		mCurCheckPosition = index;

		Cursor cursor = (Cursor) getListView().getItemAtPosition(mCurCheckPosition);

		String rowId = null;
		if (index > -1) {
			rowId = cursor.getString(cursor.getColumnIndexOrThrow(TeamTable.COLUMN_ID));
		}

		if (mDualPane) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show the data.
			getListView().setItemChecked(index, true);

			// Check what fragment is currently shown, replace if needed.
			TeamDetailsFragment details = (TeamDetailsFragment)
					getFragmentManager().findFragmentById(R.id.details);
			if (details == null || details.getShownItem() == null || !details.getShownItem().equals(rowId)) {
				details = TeamDetailsFragment.newInstance(rowId);

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.details, details);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		} else {
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			Intent intent = new Intent();
			intent.setClass(getActivity(), EditTeamActivity.class);
			intent.putExtra("rowId", rowId);
			startActivity(intent);
		}
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
				TeamTable.COLUMN_ID,
				TeamTable.COLUMN_NAME,
				TeamTable.COLUMN_LOCATION };
		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				KubbContentProvider.TEAMS_CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(data);
	}
}
