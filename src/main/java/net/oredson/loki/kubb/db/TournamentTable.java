package net.oredson.loki.kubb.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TournamentTable {

	// Database table
	public static final String TABLE_NAME = "tournaments";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
//	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_LOCATION = "location";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null, "
//			+ COLUMN_DATE + " integer null, "
			+ COLUMN_LOCATION + " text not null, "
			+ TableConstants.COLUMN_GUID + " text not null, "
			+ TableConstants.COLUMN_DIRTY + " integer default 1"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TournamentTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
}