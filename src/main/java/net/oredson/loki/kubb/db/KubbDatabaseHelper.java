package net.oredson.loki.kubb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KubbDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "lokikubb.db";
	private static final int DATABASE_VERSION = 5;

	public KubbDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		PlayerTable.onCreate(database);
		TeamTable.onCreate(database);
		TournamentTable.onCreate(database);
		TournamentTeamTable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		PlayerTable.onUpgrade(database, oldVersion, newVersion);
		TeamTable.onUpgrade(database, oldVersion, newVersion);
		TournamentTable.onUpgrade(database, oldVersion, newVersion);
		TournamentTeamTable.onUpgrade(database, oldVersion, newVersion);
	}
}
