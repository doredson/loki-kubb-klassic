package net.oredson.loki.kubb.activities;

import net.oredson.loki.kubb.R;
import android.app.Activity;
import android.os.Bundle;

public class ManagePlayerActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.players_layout);
	}
}
