package net.oredson.loki.kubb.providers;

import java.util.HashMap;
import java.util.Map;

import net.oredson.loki.kubb.KubbApplication;
import net.oredson.loki.kubb.db.PlayerTable;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class PlayerContentProvider extends ContentProvider {
	public static final String AUTHORITY = "net.oredson.loki.kubb";
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/net.oredson.loki.kubb.players";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/players");

	private static final int ALL = 1;
	private static final int SINGLE_ID = 2;

	private final UriMatcher uriMatcher;
	private final Map<String, String> projectionMap = new HashMap<String, String>();

	public PlayerContentProvider() {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PlayerTable.TABLE_NAME, ALL);
		uriMatcher.addURI(AUTHORITY, PlayerTable.TABLE_NAME + "/#", SINGLE_ID);

		projectionMap.put(PlayerTable.COLUMN_ID, PlayerTable.COLUMN_ID);
		projectionMap.put(PlayerTable.COLUMN_LNAME, PlayerTable.COLUMN_LNAME);
		projectionMap.put(PlayerTable.COLUMN_FNAME, PlayerTable.COLUMN_FNAME);
		projectionMap.put(PlayerTable.COLUMN_LOCATION, PlayerTable.COLUMN_LOCATION);
	}

	@Override
	public int delete(Uri uri, String selection, String[] whereArgs) {
		SQLiteDatabase db = KubbApplication.getDatabase();

		switch (uriMatcher.match(uri)) {
		case ALL:
			break;
		case SINGLE_ID:
			String id = uri.getLastPathSegment();
			selection = PlayerTable.COLUMN_ID + "=" + id
					+ (!TextUtils.isEmpty(selection) ?
							" AND (" + selection + ')' : "");
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		int count = db.delete(PlayerTable.TABLE_NAME, selection, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ALL:
			return CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (uriMatcher.match(uri) != ALL) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		SQLiteDatabase db = KubbApplication.getDatabase();
		long rowId = db.insert(PlayerTable.TABLE_NAME, PlayerTable.COLUMN_LNAME, values);

		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
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
		qb.setTables(PlayerTable.TABLE_NAME);
		qb.setProjectionMap(projectionMap);

		switch (uriMatcher.match(uri)) {
		case ALL:
			break;
		case SINGLE_ID:
			String id = uri.getLastPathSegment();
			qb.appendWhere(PlayerTable.COLUMN_ID + "=" + id);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase db = KubbApplication.getDatabase();
		Cursor c = qb.query(db, projection, where, selectionArgs, null, null, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] whereArgs) {
		SQLiteDatabase db = KubbApplication.getDatabase();
		int count;
		switch (uriMatcher.match(uri)) {
		case SINGLE_ID:
			String id = uri.getLastPathSegment();
			selection = PlayerTable.COLUMN_ID + "=" + id;
			count = db.update(PlayerTable.TABLE_NAME, values, selection, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
