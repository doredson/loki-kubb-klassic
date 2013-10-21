package net.oredson.loki.kubb.activities;

import net.oredson.loki.kubb.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

	/**
	 * Called when the activity is first created.
	 *
	 * @param savedInstanceState If the activity is being re-initialized after
	 *                           previously being shut down then this Bundle contains the data it most
	 *                           recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button add = (Button) findViewById(R.id.players);
		add.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Open the manage player activity
				Intent intent = new Intent(getBaseContext(), ManagePlayerActivity.class);
				startActivity(intent);
			}
		});
	}
}

