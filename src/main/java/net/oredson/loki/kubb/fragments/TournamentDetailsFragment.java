package net.oredson.loki.kubb.fragments;

import net.oredson.loki.kubb.R;
import net.oredson.loki.kubb.db.TournamentTable;
import net.oredson.loki.kubb.providers.KubbContentProvider;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TournamentDetailsFragment extends Fragment {
	private TextView tournamentName;
	private TextView location;
	private AsyncQueryHandler queryHandler;

	/**
	 * Create a new instance of DetailsFragment, initialized to
	 * show the record identified by rowId.
	 */
	public static TournamentDetailsFragment newInstance(String rowId) {
		TournamentDetailsFragment f = new TournamentDetailsFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putString("rowId", rowId);
		f.setArguments(args);

		return f;
	}

	public String getShownItem() {
		return getArguments().getString("rowId", null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		queryHandler = new AsyncQueryHandler(getActivity().getContentResolver()) {
			@Override
			protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
				if (cursor != null)
				{
					cursor.moveToFirst();
					String myFName = cursor.getString(cursor.getColumnIndexOrThrow(TournamentTable.COLUMN_NAME));
					String myLocation = cursor.getString(cursor.getColumnIndexOrThrow(TournamentTable.COLUMN_LOCATION));

					tournamentName.setText(myFName);
					location.setText(myLocation);
				}
			}
		};

		if (getShownItem() != null) {
			String[] projection = {
					TournamentTable.COLUMN_ID,
					TournamentTable.COLUMN_NAME,
					TournamentTable.COLUMN_LOCATION };
			String where = TournamentTable.COLUMN_ID + "=?";
			String[] args = new String[] { getShownItem() };

			queryHandler.startQuery(0, null, KubbContentProvider.TOURNAMENTS_CONTENT_URI, projection, where, args, null);
		}

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.edit_tournament, container, false);

		tournamentName = (TextView) v.findViewById(R.id.tournament_name);
		location = (TextView) v.findViewById(R.id.location);

		setHasOptionsMenu(true);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.edit_actions, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.delete:
			deleteOrCancel();
			resetFragment();
			return true;
		case R.id.save:
			save();
			resetFragment();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void deleteOrCancel()
	{
		if (getShownItem() != null)
		{
			String where = TournamentTable.COLUMN_ID + "=?";
			String[] args = new String[] { getShownItem() };
			queryHandler.startDelete(0, null, KubbContentProvider.TOURNAMENTS_CONTENT_URI, where, args);
		}
	}

	private void save() {
		String name = tournamentName.getText().toString();
		String loc = location.getText().toString();

		if (name.trim().equalsIgnoreCase("")) {
			Toast.makeText(getActivity().getBaseContext(), "Please ENTER name", Toast.LENGTH_LONG).show();
			return;
		}

		ContentValues values = new ContentValues();
		values.put(TournamentTable.COLUMN_NAME, name);
		values.put(TournamentTable.COLUMN_LOCATION, loc);

		// insert a record
		if (getShownItem() == null) {
			queryHandler.startInsert(0, null, KubbContentProvider.TOURNAMENTS_CONTENT_URI, values);
		}
		// update a record
		else {
			String where = TournamentTable.COLUMN_ID + "=?";
			String[] args = new String[] { getShownItem() };

			queryHandler.startUpdate(0, null, KubbContentProvider.TOURNAMENTS_CONTENT_URI, values, where, args);
		}
	}

	public void resetFragment() {
		TournamentDetailsFragment details = TournamentDetailsFragment.newInstance(null);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.details, details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
}
