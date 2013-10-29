package net.oredson.loki.kubb.activities;

import net.oredson.loki.kubb.fragments.TournamentDetailsFragment;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class EditTournamentActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getResources().getConfiguration().orientation
				== Configuration.ORIENTATION_LANDSCAPE) {
			// If the screen is now in landscape mode, we can show the
			// dialog in-line with the list so we don't need this activity.
			finish();
			return;
		}

		String rowId = null;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			rowId = extras.getString("rowId");
		}

		TournamentDetailsFragment details = TournamentDetailsFragment.newInstance(rowId);
		getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
	}
}
