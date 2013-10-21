package net.oredson.loki.kubb;

import net.oredson.loki.kubb.db.KubbDatabaseHelper;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class KubbApplication extends Application {

	private static SQLiteDatabase database;

	public static SQLiteDatabase getDatabase() {
		return database;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		database = new KubbDatabaseHelper(getApplicationContext()).getWritableDatabase();
	}

	@Override
	public void onTerminate() {
		if (database != null) {
			try {
				database.close();
			} finally {
				database = null;
			}
		}
		
		super.onTerminate();
	}
}
