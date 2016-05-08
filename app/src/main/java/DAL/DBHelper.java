package DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aviran on 07/05/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LucidDream.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDreamTable = String.format("CREATE TABLE IF NOT EXISTS %s" +
                "( %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s VARCHAR, " +
                "%s TEXT);",
                DreamContract.Dream.DREAM_TABLE_NAME,
                DreamContract.Dream._ID,
                DreamContract.Dream.COLUMN_NAME_DREAM_TITLE,
                DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT);
        Log.d("avirankatz", createDreamTable);
        db.execSQL(createDreamTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException();
    }

    public boolean insertNewDream(String dreamTitle, String dreamScript) {
        ContentValues values = new ContentValues();
        values.put(DreamContract.Dream.COLUMN_NAME_DREAM_TITLE, dreamTitle);
        values.put(DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT, dreamScript);
        try {
            getWritableDatabase().insert(DreamContract.Dream.DREAM_TABLE_NAME, null, values);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Cursor getAllDreams() {
        return getReadableDatabase().rawQuery(String.format("SELECT * FROM %s", DreamContract.Dream.DREAM_TABLE_NAME), null);
    }

    public Cursor getDreamTitles() {
        return getReadableDatabase().rawQuery(String.format("SELECT %s, %s FROM %s", DreamContract.Dream._ID, DreamContract.Dream.COLUMN_NAME_DREAM_TITLE, DreamContract.Dream.DREAM_TABLE_NAME), null);
    }

}
