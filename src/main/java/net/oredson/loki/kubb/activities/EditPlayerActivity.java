package net.oredson.loki.kubb.activities;

import net.oredson.loki.kubb.R;
import net.oredson.loki.kubb.db.PlayerTable;
import net.oredson.loki.kubb.providers.PlayerContentProvider;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditPlayerActivity extends Activity
{

	private TextView firstName;
	private TextView lastName;
	private TextView location;
	private String mode;
	private String rowid;

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

		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();
			mode = bundle.getString("mode");
			rowid = bundle.getString("rowId");
		}

		setContentView(R.layout.edit_player);

		firstName = (TextView) findViewById(R.id.fname);
		lastName = (TextView) findViewById(R.id.lname);
		location = (TextView) findViewById(R.id.location);

		Button cancel = (Button) findViewById(R.id.cancel_button);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		Button save = (Button) findViewById(R.id.save_button);
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String first = firstName.getText().toString();
				String last = lastName.getText().toString();
				String loc = location.getText().toString();

				if (first.trim().equalsIgnoreCase("")) {
					Toast.makeText(getBaseContext(), "Please ENTER first name", Toast.LENGTH_LONG).show();
					return;
				}

				// check for blanks
				if (last.trim().equalsIgnoreCase("")) {
					Toast.makeText(getBaseContext(), "Please ENTER last name", Toast.LENGTH_LONG).show();
					return;
				}

				ContentValues values = new ContentValues();
				values.put(PlayerTable.COLUMN_FNAME, first);
				values.put(PlayerTable.COLUMN_LNAME, last);
				values.put(PlayerTable.COLUMN_LOCATION, loc);

				// insert a record
				if (mode.trim().equalsIgnoreCase("add")) {
					getContentResolver().insert(PlayerContentProvider.CONTENT_URI, values);
				}
				// update a record
				else {
					Uri uri = Uri.parse(PlayerContentProvider.CONTENT_URI + "/" + rowid);
					getContentResolver().update(uri, values, null, null);
				}

				finish();
			}
		});

		if (rowid != null) {
			loadInfo();
		}
	}

	private void loadInfo() {

		String[] projection = {
				PlayerTable.COLUMN_ID,
				PlayerTable.COLUMN_FNAME,
				PlayerTable.COLUMN_LNAME,
				PlayerTable.COLUMN_LOCATION };
		Uri uri = Uri.parse(PlayerContentProvider.CONTENT_URI + "/" + rowid);
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		if (cursor != null) {
			cursor.moveToFirst();
			String myFName = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_FNAME));
			String myLName = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_LNAME));
			String myLocation = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_LOCATION));
			firstName.setText(myFName);
			lastName.setText(myLName);
			location.setText(myLocation);
		}
	}
}
