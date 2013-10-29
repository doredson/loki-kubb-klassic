package net.oredson.loki.kubb.providers;

import java.util.UUID;

import net.oredson.loki.kubb.KubbApplication;
import net.oredson.loki.kubb.db.PlayerTable;
import net.oredson.loki.kubb.db.TableConstants;
import net.oredson.loki.kubb.db.TeamTable;
import net.oredson.loki.kubb.db.TournamentTable;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class KubbContentProvider extends ContentProvider {
	public static final String AUTHORITY = "net.oredson.loki.kubb";
	public static final String PLAYER_CONTENT_TYPE = "vnd.android.cursor.dir/net.oredson.loki.kubb.players";
	public static final String TEAMS_CONTENT_TYPE = "vnd.android.cursor.dir/net.oredson.loki.kubb.teams";
	public static final String TOURNAMES_CONTENT_TYPE = "vnd.android.cursor.dir/net.oredson.loki.kubb.tournaments";
	
	public static final Uri PLAYERS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/players");
	public static final Uri TEAMS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/teams");
	public static final Uri TOURNAMENTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/tournaments");

	private static final int PLAYER_ALL = 10;
	private static final int PLAYER_ID = 11;
	private static final int TEAM_ALL = 20;
	private static final int TEAM_ID = 21;
	private static final int TOURNAMENT_ALL = 30;
	private static final int TOURNAMENT_ID = 31;

	private final UriMatcher uriMatcher;

	public KubbContentProvider() {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PlayerTable.TABLE_NAME, PLAYER_ALL);
		uriMatcher.addURI(AUTHORITY, PlayerTable.TABLE_NAME + "/#", PLAYER_ID);
		uriMatcher.addURI(AUTHORITY, TeamTable.TABLE_NAME, TEAM_ALL);
		uriMatcher.addURI(AUTHORITY, TeamTable.TABLE_NAME + "/#", TEAM_ID);
		uriMatcher.addURI(AUTHORITY, TournamentTable.TABLE_NAME, TOURNAMENT_ALL);
		uriMatcher.addURI(AUTHORITY, TournamentTable.TABLE_NAME + "/#", TOURNAMENT_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] whereArgs) {
		SQLiteDatabase db = KubbApplication.getDatabase();

		String tableName = null;
		
		switch (uriMatcher.match(uri)) {
		case PLAYER_ALL:
			tableName = PlayerTable.TABLE_NAME;
			break;
		case TEAM_ALL:
			tableName = TeamTable.TABLE_NAME;
			break;
		case TOURNAMENT_ALL:
			tableName = TeamTable.TABLE_NAME;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		int count = db.delete(tableName, selection, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case PLAYER_ALL:
			return PLAYER_CONTENT_TYPE;
		case TEAM_ALL:
			return TEAMS_CONTENT_TYPE;
		case TOURNAMENT_ALL:
			return TOURNAMES_CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		String tableName = null;
		Uri contentURI = null;
		
		switch (uriMatcher.match(uri)) {
		case PLAYER_ALL:
			tableName = PlayerTable.TABLE_NAME;
			contentURI = PLAYERS_CONTENT_URI;
			break;
		case TEAM_ALL:
			tableName = TeamTable.TABLE_NAME;
			contentURI = TEAMS_CONTENT_URI;
			break;
		case TOURNAMENT_ALL:
			tableName = TournamentTable.TABLE_NAME;
			contentURI = TOURNAMENTS_CONTENT_URI;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		values.put(TableConstants.COLUMN_DIRTY, 1);
		values.put(TableConstants.COLUMN_GUID, UUID.randomUUID().toString());

		SQLiteDatabase db = KubbApplication.getDatabase();
		long rowId = db.insert(tableName, null, values);

		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(contentURI, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String where, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String tableName;
		String id = uri.getLastPathSegment();
		
		switch (uriMatcher.match(uri)) {

		case PLAYER_ALL:
			tableName = PlayerTable.TABLE_NAME;
			break;
		case TEAM_ALL:
			tableName = TeamTable.TABLE_NAME;
			break;
		case TOURNAMENT_ID:
			qb.appendWhere(TournamentTable.COLUMN_ID + "=" + id);
		case TOURNAMENT_ALL:
			tableName = TournamentTable.TABLE_NAME;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		qb.setTables(tableName);
		SQLiteDatabase db = KubbApplication.getDatabase();
		Cursor c = qb.query(db, projection, where, selectionArgs, null, null, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		String tableName;

		switch (uriMatcher.match(uri)) {
		case PLAYER_ALL:
			tableName = PlayerTable.TABLE_NAME;
			break;
		case TEAM_ALL:
			tableName = TeamTable.TABLE_NAME;
			break;
		case TOURNAMENT_ALL:
			tableName = TournamentTable.TABLE_NAME;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		SQLiteDatabase db = KubbApplication.getDatabase();
		int count = db.update(tableName, values, where, whereArgs);

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
