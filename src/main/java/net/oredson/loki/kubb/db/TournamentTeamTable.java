package net.oredson.loki.kubb.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TournamentTeamTable {

	// Database table
	public static final String TABLE_NAME = "tournament_team_player";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TOURNAMENT_TEAM_ID = "tournament_team_id";
	public static final String COLUMN_PLAYER_ID = "player_id";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_TOURNAMENT_TEAM_ID + " integer not null, "
			+ COLUMN_PLAYER_ID + " integer not null, "
			+ TableConstants.COLUMN_GUID + " text not null, "
			+ TableConstants.COLUMN_DIRTY + " integer default 1"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TournamentTeamTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
}