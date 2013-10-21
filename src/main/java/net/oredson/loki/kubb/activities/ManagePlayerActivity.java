package net.oredson.loki.kubb.activities;

import net.oredson.loki.kubb.R;
import net.oredson.loki.kubb.db.PlayerTable;
import net.oredson.loki.kubb.providers.PlayerContentProvider;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ManagePlayerActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>
{

	private SimpleCursorAdapter dataAdapter;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after
	 *            previously being shut down then this Bundle contains the data
	 *            it most
	 *            recently supplied in onSaveInstanceState(Bundle). <b>Note:
	 *            Otherwise it is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_player);

		Button add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// open the edit player activity in "add" mode
				Intent intent = new Intent(getBaseContext(), EditPlayerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("mode", "add");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		displayListView();
	}

	private void displayListView() {

		// The desired columns to be bound
		String[] columns = new String[] {
				PlayerTable.COLUMN_FNAME,
				PlayerTable.COLUMN_LNAME,
				PlayerTable.COLUMN_LOCATION
		};

		// the XML defined views which the data will be bound to
		int[] to = new int[] {
				R.id.fname,
				R.id.lname,
				R.id.location,
		};

		// create an adapter from the SimpleCursorAdapter
		dataAdapter = new SimpleCursorAdapter(
				this,
				R.layout.player_detail,
				null,
				columns,
				to,
				0);

		// get reference to the ListView
		ListView listView = (ListView) findViewById(R.id.playerList);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
		// Ensures a loader is initialized and active.
		getLoaderManager().initLoader(0, null, this);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView listView, View view,
					int position, long id) {
				// Get the cursor, positioned to the corresponding row in the
				// result set
				Cursor cursor = (Cursor) listView.getItemAtPosition(position);

				// display the selected country
				String firstName =
						cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_FNAME));
				Toast.makeText(getApplicationContext(),
						firstName, Toast.LENGTH_SHORT).show();

				String rowId =
						cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_ID));

				// starts a new Intent to update/delete a Country
				// pass in row Id to create the Content URI for a single row
				Intent intent = new Intent(getBaseContext(), EditPlayerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("mode", "update");
				bundle.putString("rowId", rowId);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		dataAdapter.swapCursor(null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		dataAdapter.swapCursor(data);
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
				PlayerTable.COLUMN_ID,
				PlayerTable.COLUMN_FNAME,
				PlayerTable.COLUMN_LNAME,
				PlayerTable.COLUMN_LOCATION };
		CursorLoader cursorLoader = new CursorLoader(this,
				PlayerContentProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}
}
